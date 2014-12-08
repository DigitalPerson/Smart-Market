using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.Security;
using System.Configuration;
using System.Data.SqlClient;

public partial class EditUser : System.Web.UI.Page
{
    private String queryStringEmail = null;
    private String loginEmail = null;
    private String email = null;
    private User user = null;
    User loggedInUser = null;
    SmartMarketDataClassesDataContext db;
    protected void Page_Load(object sender, EventArgs e)
    {
        queryStringEmail = Request.QueryString["email"];
        loginEmail = HttpContext.Current.User.Identity.Name;
        db = new SmartMarketDataClassesDataContext();
        loggedInUser = db.Users.SingleOrDefault(u => u.email.Equals(loginEmail));
        if (queryStringEmail == null) //for normal users
        {
            email = loginEmail;
        }
        else //edit by query string parameter for admins
        {      
            if (loggedInUser.admin)
            {
                email = queryStringEmail;
            }
            else
            {
                String info = Strings.accessDenied;
                Response.Redirect("Info.aspx?info=" + info);
            }
        }
        user = db.Users.SingleOrDefault(u => u.email.Equals(email));
        if (user == null)
        {
            String info = Strings.urlNotFound;
            Response.Redirect("Info.aspx?info=" + info);
        }
        if (!IsPostBack)
        {
            setDeveloperOptions(user);
            setAdminOptions(user, loggedInUser);
            firstName_TextBox.Text = user.firstName;
            lastName_TextBox.Text = user.lastName;
            gender_DropDownList.SelectedValue = user.gender[0].ToString();
            country_DropDownList.SelectedValue = user.countryID.ToString();
            email_TextBox.Text = user.email;
            admin_CheckBox.Checked = user.admin;
        }
    }
    protected void submit_Button_Click(object sender, EventArgs e)
    {
        user.firstName = firstName_TextBox.Text;
        user.lastName = lastName_TextBox.Text;
        user.gender = gender_DropDownList.SelectedValue;
        user.countryID = Int32.Parse(country_DropDownList.SelectedValue);
        user.email = email_TextBox.Text;
        if (!password_TextBox.Text.Equals(""))
        {
            user.password = FormsAuthentication.HashPasswordForStoringInConfigFile(password_TextBox.Text, "MD5");
        }
        user.admin = admin_CheckBox.Checked;
        try
        {
            db.SubmitChanges();
            MessageBox.Show(Strings.accountUpdatedSuccessfully);
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
    private void setDeveloperOptions(User user)
    {
        if (user.Developer != null)
        {
            editDeveloperInfo_HyperLink.Visible = true;
            editDeveloperInfo_HyperLink.NavigateUrl = "~/EditDeveloper.aspx?email=" + user.email;
        }
        else
        {
            registerAsDeveloper_HyperLink.Visible = true;
            registerAsDeveloper_HyperLink.NavigateUrl = "~/RegisterDeveloper.aspx?email=" + user.email;
        }
    }
    private void setAdminOptions(User user, User loggedInUser)
    {
        if (loggedInUser.admin)
        {
            admin_CheckBox.Visible = true;
            admin_Label.Visible = true;           
        }
    }
}