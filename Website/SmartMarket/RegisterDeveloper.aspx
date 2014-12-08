<%@ Page Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true" CodeFile="RegisterDeveloper.aspx.cs" Inherits="RegisterDeveloper" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head_ContentPlaceHolder" runat="Server">
    <title>Register as developer</title>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="body_ContentPlaceHolder" runat="Server">
    <div id="gallery">
        <br />
        <br />
        <br />
        <br />
        <br />
        <table style="width: 50%;">
        <tr>
            <td class="style1">
                <asp:Label ID="developerName_Label" runat="server" Text="Developer Name"></asp:Label>
            </td>
            <td>
                <asp:TextBox ID="developerName_TextBox" runat="server" CssClass="fields"></asp:TextBox>
                <br />
                <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" ControlToValidate="developerName_TextBox"
                    Display="Dynamic" ErrorMessage="Developer name is required field."></asp:RequiredFieldValidator>
            </td>
        </tr>
        <tr>
            <td class="style1">
                <asp:Label ID="supportEmail_Label" runat="server" Text="Support Email"></asp:Label>
            </td>
            <td>
                <asp:TextBox ID="supportEmail_TextBox" runat="server" CssClass="fields"></asp:TextBox>
                <br />
                <asp:RegularExpressionValidator ID="RegularExpressionValidator1" runat="server" ControlToValidate="supportEmail_TextBox"
                    Display="Dynamic" ErrorMessage="Email isn't valid." 
                    ValidationExpression="\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*"></asp:RegularExpressionValidator>
            </td>
        </tr>
        <tr>
            <td class="style1">
                <asp:Label ID="website_Label" runat="server" Text="Website"></asp:Label>
            </td>
            <td>
                <asp:TextBox ID="website_TextBox" runat="server" CssClass="fields"></asp:TextBox>
                <br />
                <asp:RegularExpressionValidator ID="RegularExpressionValidator2" runat="server" ControlToValidate="website_TextBox"
                    Display="Dynamic" ErrorMessage="Website isn't valid." ValidationExpression="http(s)?://([\w-]+\.)+[\w-]+(/[\w- ./?%&amp;=]*)?"></asp:RegularExpressionValidator>
            </td>
        </tr>
        <tr>
            <td class="style1">
                <asp:Label ID="phone_Label" runat="server" Text="Phone"></asp:Label>
            </td>
            <td>
                <asp:TextBox ID="phone_TextBox" runat="server" CssClass="fields"></asp:TextBox>
                <br />
                <asp:RegularExpressionValidator ID="RegularExpressionValidator3" runat="server" 
                    ControlToValidate="phone_TextBox" Display="Dynamic" 
                    ErrorMessage="Phone isn't valid." ValidationExpression="^\+\d{10,15}$"></asp:RegularExpressionValidator>
            </td>
        </tr>
    </table>
        <br />
        <br />
    <asp:Button ID="submit_Button" runat="server" OnClick="submit_Button_Click" 
            Text="Submit" CssClass="button" />
    &nbsp;
    </div>
</asp:Content>
