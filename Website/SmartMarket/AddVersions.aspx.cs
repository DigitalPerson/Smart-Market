using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.IO;
using System.Data.SqlClient;
using System.Drawing.Imaging;

public partial class AddVersions : System.Web.UI.Page
{
    private App app;
    private SmartMarketDataClassesDataContext db;
    private int appID;
    private String appsPath;
    private String iconsPath;
    private String tempIconsPath;
    private String tempAppsPath;
    private String tempIconsPathNotMapped;
    private String qrCodesPathNotMapped;
    private String qrCodesPath;
    private String qrCodesDataPrefix;
    private String aaptPath;
    protected void Page_Load(object sender, EventArgs e)
    {
        appsPath = Server.MapPath(Config.appsPathNotMapped);
        iconsPath = Server.MapPath(Config.iconsPathNotMapped);
        tempIconsPathNotMapped = Config.tempIconsPathNotMapped;
        tempIconsPath = Server.MapPath(tempIconsPathNotMapped);
        tempAppsPath = Server.MapPath(Config.tempAappsPathNotMapped);
        qrCodesPathNotMapped = Config.qrCodesPathNotMapped;
        qrCodesPath = Server.MapPath(qrCodesPathNotMapped);
        qrCodesDataPrefix = Config.qrCodesDataPrefix;
        aaptPath = Server.MapPath(Config.aaptPathNotMapped);
        String loginEmail = HttpContext.Current.User.Identity.Name;
        if (Request.QueryString["id"] == null)
        {
            String info = Strings.urlNotFound;
            Response.Redirect("Info.aspx?info=" + info);
        }
        appID = Int32.Parse(Request.QueryString["id"]);
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
        SetVersionTable();
        SetPublishAppButton();
    }
    protected void upload_Button_Click(object sender, EventArgs e)
    {
        Helper.UploadFile(this, version_FileUpload, tempAppsPath, appID.ToString());
        String aaptOutput = Helper.ExecuteCommand(aaptPath, "dump badging " + tempAppsPath + appID.ToString() + ".apk");
        String packageName = Helper.GetApkPropertyValue(aaptOutput, "package: name=");
        if (packageName != null)
        {
            if (!app.packageName.Contains('.') || (app.packageName.Contains('.') && app.packageName.Equals(packageName))) // if packageName of the app is not set or its equal to the packageName of the uploaded file then its ok
            {
                Version version = new Version();
                version.App = app;
                version.versionNumber = Helper.GetApkPropertyValue(aaptOutput, "versionName=");
                FileInfo fileInfo = new FileInfo(tempAppsPath + appID.ToString() + ".apk");
                version.size = (float)(fileInfo.Length) / 1024 / 1024;
                version.minAndroidVersion = Helper.GetApkPropertyValue(aaptOutput, "sdkVersion:");
                version.date = DateTime.Now;
                db.Versions.InsertOnSubmit(version);
                app.packageName = packageName;
                try
                {
                    db.SubmitChanges();
                    File.Move(tempAppsPath + appID.ToString() + ".apk", appsPath + version.versionID + ".apk");
                    SetVersionTable();
                }
                catch (SqlException ex)
                {
                    if (ex.Number == 2627)
                    {
                        MessageBox.Show(Strings.packageOrVersionAlreadyExists);
                    }
                    else
                    {
                        MessageBox.Show(Strings.unknownErrorInDatabase);
                    }
                    File.Delete(tempAppsPath + appID.ToString() + ".apk");
                }
            }
            else
            {
                MessageBox.Show(Strings.itIsNotPossibleToChangePackageName);
                File.Delete(tempAppsPath + appID.ToString() + ".apk");
            }
        }
        else
        {
            MessageBox.Show(Strings.apkFileIsCorrupted);
        }
    }
    private void SetVersionTable()
    {
        var versions = db.Versions.Where(v => v.appID == appID);
        TableRow tableRow;
        TableCell tableCell;
        Button button;
        versions_Table.Rows.Clear();
        tableRow = new TableRow();
        tableRow.BackColor = System.Drawing.Color.FromName("#FFFF00");
        tableRow.Font.Bold = true;
        tableRow.ForeColor = System.Drawing.Color.FromName("#6C8085");
        //-------------------------------------------
        tableCell = new TableCell();
        tableCell.Text = "Version Number";
        tableRow.Cells.Add(tableCell);
        //-------------------------------------------
        tableCell = new TableCell();
        tableCell.Text = "Size";
        tableRow.Cells.Add(tableCell);
        //-------------------------------------------
        tableCell = new TableCell();
        tableCell.Text = "Min Android API";
        tableRow.Cells.Add(tableCell);
        //-------------------------------------------
        tableCell = new TableCell();
        tableCell.Text = "Date";
        tableRow.Cells.Add(tableCell);
        //-------------------------------------------
        tableCell = new TableCell();
        tableCell.Text = "";
        tableRow.Cells.Add(tableCell);
        //-------------------------------------------
        tableCell = new TableCell();
        tableCell.Text = "";
        tableRow.Cells.Add(tableCell);
        //-------------------------------------------
        versions_Table.Rows.Add(tableRow);  
        foreach (var v in versions)
        {
            tableRow = new TableRow();
            //-------------------------------------------
            tableCell = new TableCell();
            tableCell.Text = v.versionNumber;
            tableRow.Cells.Add(tableCell);
            //-------------------------------------------
            tableCell = new TableCell();
            tableCell.Text = v.size.ToString();
            tableRow.Cells.Add(tableCell);
            //-------------------------------------------
            tableCell = new TableCell();
            tableCell.Text = v.minAndroidVersion;
            tableRow.Cells.Add(tableCell);
            //-------------------------------------------
            tableCell = new TableCell();
            tableCell.Text = v.date.ToLongDateString();
            tableRow.Cells.Add(tableCell);
            //-------------------------------------------
            tableCell = new TableCell();
            button = new Button();
            button.ID = "bDelete"+ v.versionID;
            button.Text = "Delete";
            button.Click += new System.EventHandler(this.DeleteVersion); 
            button.CausesValidation = false;
            button.CssClass = "button";
            button.CommandArgument = v.versionID.ToString();
            tableCell.Controls.Add(button);
            tableRow.Cells.Add(tableCell);
            //-------------------------------------------
            tableCell = new TableCell();
            button = new Button();
            if (v.App.activatedVersionID == v.versionID)
            {
                button.ID = "bDeactivate" + v.versionID;
                button.Text = "Deactivate";
                button.Click += new System.EventHandler(this.DeactivateVersion);
            }
            else
            {
                button.ID = "bActivate" + v.versionID;
                button.Text = "Activate";
                button.Click += new System.EventHandler(this.ActivateVersion);
            }
            button.CausesValidation = false;
            button.CssClass = "button";
            button.CommandArgument = v.versionID.ToString();
            tableCell.Controls.Add(button);
            tableRow.Cells.Add(tableCell);
            //-------------------------------------------
            versions_Table.Rows.Add(tableRow);
        }
    }
    protected void DeleteVersion(object sender, EventArgs e)
    {
        int versionID = Int32.Parse((String)((Button)sender).CommandArgument);
        Version version = db.Versions.Single(v => v.versionID.Equals(versionID));
        String packageName = version.App.packageName;
        if (version.App.activatedVersionID.Equals(version.versionID))  //deactivate version and unpublish the app if the deleted version is activated
        {
            version.App.activatedVersionID = null;
            app.published = false;
        }
        db.Versions.DeleteOnSubmit(version);
        try
        {
            db.SubmitChanges();
            String filePath = appsPath + version.versionID + ".apk";
            File.Delete(filePath);
            SetVersionTable();
            SetPublishAppButton();
        }
        catch (SqlException ex)
        {
            MessageBox.Show(Strings.unknownErrorInDatabase);
        }
    }
    private void ActivateVersion(object sender, EventArgs e)
    {
        int versionID = Int32.Parse((String)((Button)sender).CommandArgument);
        Version version = db.Versions.Single(v => v.versionID.Equals(versionID));
        version.App.activatedVersionID = version.versionID;
        try
        {
            db.SubmitChanges();
            Helper.GetApkIcon(aaptPath, appsPath + versionID + ".apk", tempIconsPathNotMapped + versionID, iconsPath, app.appID.ToString());
            System.Drawing.Image qrCode = Helper.GenerateQRCode(qrCodesDataPrefix + app.packageName);
            qrCode.Save(qrCodesPath + app.appID + ".png", ImageFormat.Png);
            SetVersionTable();
            SetPublishAppButton();
        }
        catch (SqlException ex)
        {
            MessageBox.Show(Strings.unknownErrorInDatabase);
        }
    }
    private void DeactivateVersion(object sender, EventArgs e)
    {
        int versionID = Int32.Parse((String)((Button)sender).CommandArgument);
        Version version = db.Versions.Single(v => v.versionID.Equals(versionID));
        if (version.App.activatedVersionID.Equals(version.versionID))
        {
            version.App.activatedVersionID = null;
            app.published = false;
        }
        try
        {
            db.SubmitChanges();
            SetVersionTable();
            SetPublishAppButton();
        }
        catch (SqlException ex)
        {
            MessageBox.Show(Strings.unknownErrorInDatabase);
        }
    }
    private void SetPublishAppButton()
    {
        if (app.published)
        {
            publishApp_Button.Text = "Unpublish App";
        }
        else
        {
            publishApp_Button.Text = "Publish App";
        }
    }
    protected void publishApp_Button_Click(object sender, EventArgs e)
    {
        if (app.activatedVersionID == null)
        {
            MessageBox.Show(Strings.appMustHaveActivatedVersion);
        }
        else
        {
            app.published = !app.published;
            try
            {
                db.SubmitChanges();
                SetPublishAppButton();
            }
            catch (SqlException ex)
            {
                MessageBox.Show(Strings.unknownErrorInDatabase);
            }
        }
    }
}