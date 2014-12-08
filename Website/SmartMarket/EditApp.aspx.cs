using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.IO;

public partial class EditApp : System.Web.UI.Page
{
    private App app;
    private int appID;
    private SmartMarketDataClassesDataContext db;
    protected void Page_Load(object sender, EventArgs e)
    {
        if (Request.QueryString["id"] == null)
        {
            String info = Strings.urlNotFound;
            Response.Redirect("Info.aspx?info=" + info);
        }
        appID = Int32.Parse(Request.QueryString["id"]);
        String loginEmail = HttpContext.Current.User.Identity.Name;
        db = new SmartMarketDataClassesDataContext();
        app = db.Apps.SingleOrDefault(a => a.appID.Equals(appID));
        if (app == null)
        {
            String info = Strings.urlNotFound;
            Response.Redirect("Info.aspx?info=" + info);
        }
        User user = db.Users.SingleOrDefault(u => u.email.Equals(loginEmail));
        if (user == null || (!user.admin && !app.Developer.User.email.Equals(loginEmail)))
        {
            String info = Strings.accessDenied;
            Response.Redirect("Info.aspx?info=" + info);
        }
        if (!IsPostBack)
        {
            name_TextBox.Text = app.name;
            appType_DropDownList.SelectedValue = app.Category.appTypeID.ToString();
            appType_DropDownList.DataBind();
            category_DropDownList.SelectedValue = app.categoryID.ToString();
            category_DropDownList.DataBind();
            description_TextBox.Text = app.description;
            whatIsNew_TextBox.Text = app.whatIsNew;     
        }
    }
    protected void continue_Button_Click(object sender, EventArgs e)
    {
        app.categoryID = Int32.Parse(category_DropDownList.SelectedValue);
        app.name = name_TextBox.Text;
        app.description = description_TextBox.Text;
        app.whatIsNew = whatIsNew_TextBox.Text;
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