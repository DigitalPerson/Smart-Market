<%@ Page Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true"
    CodeFile="AddScreenshots.aspx.cs" Inherits="AddScreenshots" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head_ContentPlaceHolder" runat="Server">
    <title>Add screenshot</title>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="body_ContentPlaceHolder" runat="Server">
    <div id="gallery">
        <br />
        <br />
        <br />
        <br />
        <br />
        <asp:Label ID="Label1" runat="server" Text="browse for screenshots to upload, acceptable size is 480*800."></asp:Label>
        <br />
        <br />
        <asp:FileUpload ID="screenshot_FileUpload" runat="server" />
        <br />
        <asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server" ControlToValidate="screenshot_FileUpload"
            Display="Dynamic" ErrorMessage="Select image to upload"></asp:RequiredFieldValidator>
        <asp:RegularExpressionValidator ID="RegularExpressionValidator1" runat="server" ControlToValidate="screenshot_FileUpload"
            Display="Dynamic" ErrorMessage="Only png, jpg icons are allowed" ValidationExpression=".*\.(jpg|JPG|png|PNG)$"></asp:RegularExpressionValidator>
        <br />
        <asp:Button ID="upload_Button" runat="server" OnClick="upload_Button_Click" Text="Upload"
            CssClass="button" />
        <br />
        <br />
        <asp:GridView ID="screenshots_GridView" runat="server" AutoGenerateColumns="False"
            DataKeyNames="screenshotID" DataSourceID="screenShots_LinqDataSource" AllowPaging="True"
            PageSize="15">
            <Columns>
                <asp:TemplateField HeaderText="Screenshot">
                    <ItemTemplate>
                        <asp:Image ID="Image1" runat="server" ImageUrl='<%# Eval("screenshotID", "Files/Screenshots/{0}") + Eval("extension", "{0}") %>' />
                    </ItemTemplate>
                </asp:TemplateField>
                <asp:CommandField ShowDeleteButton="True" />
            </Columns>
            <HeaderStyle BackColor="#FFFF00" Font-Bold="True" ForeColor="#6C8085" />
        </asp:GridView>
        <asp:LinqDataSource ID="screenShots_LinqDataSource" runat="server" ContextTypeName="SmartMarketDataClassesDataContext"
            EntityTypeName="" TableName="Screenshots" Where="App.appID == @App" EnableDelete="True"
            OnDeleting="OnScreenshotDelete">
            <WhereParameters>
                <asp:QueryStringParameter Name="App" QueryStringField="id" Type="Int32" />
            </WhereParameters>
        </asp:LinqDataSource>
        <br />
        <br />
        <asp:Button ID="Continue_Button" runat="server" CausesValidation="False" OnClick="Continue_Button_Click"
            Text="Continue" CssClass="button" />
        <br />
    </div>
</asp:Content>
