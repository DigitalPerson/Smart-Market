using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.Security;
using System.Data.SqlClient;

public partial class ResetPassword : System.Web.UI.Page
{
    private SmartMarketDataClassesDataContext db;
    private User user;
    protected void Page_Load(object sender, EventArgs e)
    {
        String email = Request.QueryString["email"];
        String code = Request.QueryString["code"];
        if (email == null || code == null)
        {
            String info = Strings.accessDenied;
            Response.Redirect("Info.aspx?info=" + info);
        }
        db = new SmartMarketDataClassesDataContext();
        user = db.Users.SingleOrDefault(u => u.email.Equals(email) && u.password.Equals(code));
        if (user == null)
        {
            String info = Strings.accessDenied;
            Response.Redirect("Info.aspx?info=" + info);
        }
    }
    protected void submit_Button_Click(object sender, EventArgs e)
    {
        user.password = FormsAuthentication.HashPasswordForStoringInConfigFile(password_TextBox.Text, "MD5");
        try
        {
            db.SubmitChanges();
            String info = Strings.passwordChangedSuccessfully;
            Response.Redirect("Info.aspx?info=" + info);
        }
        catch (SqlException ex)
        {
            MessageBox.Show(Strings.unknownErrorInDatabase);
        }
    }
}