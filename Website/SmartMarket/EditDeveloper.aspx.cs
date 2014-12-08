using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.Security;
using System.Configuration;
using System.Data.SqlClient;

public partial class EditDeveloper : System.Web.UI.Page
{
    private String email = null;
    private Developer developer = null;
    SmartMarketDataClassesDataContext db;
    protected void Page_Load(object sender, EventArgs e)
    {
        db = new SmartMarketDataClassesDataContext();
        string queryStringEmail = Request.QueryString["email"];
        string loginEmail = HttpContext.Current.User.Identity.Name;
        if (queryStringEmail == null)
        {
            String info = Strings.urlNotFound;
            Response.Redirect("Info.aspx?info=" + info);
        }
        developer = db.Developers.SingleOrDefault(d => d.User.email.Equals(queryStringEmail));
        User loggedInUser = db.Users.SingleOrDefault(u => u.email.Equals(loginEmail));
        if (developer == null)
        {
            String info = Strings.urlNotFound;
            Response.Redirect("Info.aspx?info=" + info);
        }
        if (!queryStringEmail.Equals(loginEmail, StringComparison.InvariantCultureIgnoreCase) && !loggedInUser.admin)
        {
            String info = Strings.accessDenied;
            Response.Redirect("Info.aspx?info=" + info);
        }
        if (!IsPostBack)
        {
            developerName_TextBox.Text = developer.name;
            website_TextBox.Text = developer.website;
            phone_TextBox.Text = developer.phone;
            supportEmail_TextBox.Text = developer.supportEmail;   
        }
    }
    protected void submit_Button_Click(object sender, EventArgs e)
    {
        developer.name = developerName_TextBox.Text;
        developer.website = website_TextBox.Text;
        developer.phone = phone_TextBox.Text;
        developer.supportEmail = supportEmail_TextBox.Text;
        try
        {
            db.SubmitChanges();
            MessageBox.Show(Strings.accountUpdatedSuccessfully);
        }
        catch (SqlException ex)
        {
            MessageBox.Show(Strings.unknownErrorInDatabase);
        }
    }
    protected void findApps_LinkButton_Click(object sender, EventArgs e)
    {
        Response.Redirect("Search.aspx?developer=" + developer.developerID);
    }
}