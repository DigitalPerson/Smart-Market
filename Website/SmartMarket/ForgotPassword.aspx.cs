using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class ForgotPassword : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void submit_Button_Click(object sender, EventArgs e)
    {
        SmartMarketDataClassesDataContext db = new SmartMarketDataClassesDataContext();
        User user = db.Users.SingleOrDefault(u => u.email.Equals(email_TextBox.Text));
        if (user != null)
        {
            String url = "http://www.digital-soft.org/SmartMarket/ResetPassword.aspx?email=" + user.email + "&code=" + user.password;
            String messageText = Strings.forgotPasswordEmail + url;
            Helper.SendMail("support@digital-soft.org", user.email, "Reset Password", messageText);
        }
        else
        {
            String info = Strings.emailNotFound; ;
            Response.Redirect("Info.aspx?info=" + info);
        }

    }


}