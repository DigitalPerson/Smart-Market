using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.IO;
using System.Data.SqlClient;

public partial class AddApp : System.Web.UI.Page
{
    private Developer developer;
    private App app;
    private SmartMarketDataClassesDataContext db;
    protected void Page_Load(object sender, EventArgs e)
    {
        string loginEmail = HttpContext.Current.User.Identity.Name;
        db= new SmartMarketDataClassesDataContext();
        developer = db.Developers.SingleOrDefault(d => d.User.email.Equals(loginEmail));
        if (developer == null)
        {
            String info = Strings.accessDenied;
            Response.Redirect("Info.aspx?info=" + info);
        }
    }
    protected void continue_Button_Click(object sender, EventArgs e)
    {
        app = new App();
        app.categoryID = Int32.Parse(category_DropDownList.SelectedValue);
        app.Developer = developer;
        app.name = name_TextBox.Text;
        app.packageName = DateTime.Now.ToString();  // set a temporary valuse because pcakageName cant be null
        app.installs = 0;
        app.description = description_TextBox.Text;
        app.whatIsNew = whatIsNew_TextBox.Text;
        app.rate = 0;
        app.ratesCount = 0;
        app.published = false;
        db.Apps.InsertOnSubmit(app);
        try
        {
            db.SubmitChanges();
            Response.Redirect("AddScreenshots.aspx?id=" + app.appID);
        }
        catch (SqlException ex)
        {
            MessageBox.Show(Strings.unknownErrorInDatabase);
        }
    }

}