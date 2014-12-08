<%@ Page Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true"
    CodeFile="AddVersions.aspx.cs" Inherits="AddVersions" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head_ContentPlaceHolder" runat="Server">
    <title>Add Versions</title>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="body_ContentPlaceHolder" runat="Server">
    <div id="gallery">
        <br />
        <br />
        <br />
        <asp:Label ID="Label1" runat="server" Text="browse for APK file to upload."></asp:Label>
        <br />
        <br />
        <asp:FileUpload ID="version_FileUpload" runat="server" />
        <div>
            <asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server" ControlToValidate="version_FileUpload"
                Display="Dynamic" ErrorMessage="Select file to upload"></asp:RequiredFieldValidator>
            <asp:RegularExpressionValidator ID="RegularExpressionValidator1" runat="server" ControlToValidate="version_FileUpload"
                Display="Dynamic" ErrorMessage="Only apk file are allowed" ValidationExpression=".*\.(apk|APK)$"></asp:RegularExpressionValidator>
            <br />
            <asp:Button ID="upload_Button" runat="server" Text="Upload" OnClick="upload_Button_Click"
                Style="height: 26px" CssClass="button" />
            <br />
            <br />
            <br />
            <br />
            <asp:Table ID="versions_Table" runat="server" GridLines="Both">
            </asp:Table>
            <br />
            <asp:Button ID="publishApp_Button" runat="server" CausesValidation="False" OnClick="publishApp_Button_Click"
                Text="Publish App" CssClass="button" />
            <br />
        </div>
    </div>
</asp:Content>
