using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.Security;
using System.Configuration;
using System.Data.SqlClient;

public partial class Register : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        Boolean loggedIn = HttpContext.Current.User.Identity.IsAuthenticated;
        if (loggedIn)
        {
            String info = Strings.youHaveAlreadyRegisterd;
            Response.Redirect("Info.aspx?info=" + info);
        }
    }
    protected void submit_Button_Click(object sender, EventArgs e)
    {
        using (SmartMarketDataClassesDataContext db = new SmartMarketDataClassesDataContext())
        {
            User user = new User();
            user.firstName = firstName_TextBox.Text;
            user.lastName = lastName_TextBox.Text;
            user.gender = gender_DropDownList.SelectedValue;
            user.countryID = Int32.Parse(country_DropDownList.SelectedValue);
            user.email = email_TextBox.Text;
            user.password = FormsAuthentication.HashPasswordForStoringInConfigFile(password_TextBox.Text, "MD5");
            db.Users.InsertOnSubmit(user);
            try
            {
                db.SubmitChanges();
                String info = Strings.accountCreatedSuccessfully;
                Response.Redirect("Info.aspx?info=" + info);
            }
            catch (SqlException ex)
            {
                if (ex.Number == 2627)
                {
                    MessageBox.Show(Strings.emailAlreadyExists);
                }
                else
                {
                    MessageBox.Show(Strings.unknownErrorInDatabase);
                }
            }
        }
    }

}