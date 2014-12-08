using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.Security;

public partial class Login : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void submit_Button_Click(object sender, EventArgs e)
    {
        User user = CheckUser(email_TextBox.Text, password_TextBox.Text);
        if (user != null)
        {
            FormsAuthentication.RedirectFromLoginPage(user.email, rememberMe_CheckBox.Checked);
        }
        else
        {
            MessageBox.Show(Strings.invalidLoginInfo);
        }
    }
    protected User CheckUser(String email, String password)
    {
        User result = null;
        String hashedPassword = FormsAuthentication.HashPasswordForStoringInConfigFile(password, "MD5"); ;
        SmartMarketDataClassesDataContext db = new SmartMarketDataClassesDataContext();
        result = db.Users.SingleOrDefault(u => u.email.ToLower().Equals(email.ToLower()) && u.password.Equals(hashedPassword));
        return result;
    }
}