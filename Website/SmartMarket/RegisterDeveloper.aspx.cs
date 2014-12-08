using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.Security;
using System.Configuration;
using System.Data.SqlClient;

public partial class RegisterDeveloper : System.Web.UI.Page
{
    SmartMarketDataClassesDataContext db;
    String email;
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
        User user = db.Users.SingleOrDefault(u => u.email.Equals(queryStringEmail));
        User loggedInUser = db.Users.SingleOrDefault(u => u.email.Equals(loginEmail));
        if (user == null)
        {
            String info = Strings.urlNotFound;
            Response.Redirect("Info.aspx?info=" + info);
        }
        if (!queryStringEmail.Equals(loginEmail, StringComparison.InvariantCultureIgnoreCase) && !loggedInUser.admin)
        {
            String info = Strings.accessDenied;
            Response.Redirect("Info.aspx?info=" + info);
        }
        if (user.Developer != null)
        {
            String info = Strings.userAlreadyRegisterdAsDeveloper;
            Response.Redirect("Info.aspx?info=" + info);
        }
        email = queryStringEmail;
    }
    protected void submit_Button_Click(object sender, EventArgs e)
    {
        Developer developer = new Developer();
        developer.User = db.Users.Single(u => u.email.Equals(email));
        developer.name = developerName_TextBox.Text;
        developer.website = website_TextBox.Text;
        developer.phone = phone_TextBox.Text;
        developer.supportEmail = supportEmail_TextBox.Text;
        db.Developers.InsertOnSubmit(developer);
        try
        {
            db.SubmitChanges();
            String info = Strings.accountCreatedSuccessfully;
            Response.Redirect("Info.aspx?info=" + info);
        }
        catch (SqlException ex)
        {
            MessageBox.Show(Strings.unknownErrorInDatabase);
        }
    }
}