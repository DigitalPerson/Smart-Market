<%@ Page Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true"
    CodeFile="ForgotPassword.aspx.cs" Inherits="ForgotPassword" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head_ContentPlaceHolder" runat="Server">
    <title>Forgot Password</title>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="body_ContentPlaceHolder" runat="Server">
    <div id="gallery">
        <br />
        <br />
        <br />
        <br />
        <br />
        <asp:Panel ID="Panel1" runat="server" DefaultButton="submit_Button">
        <table style="width: 50%;">
            <tr>
                <td class="style1">
                    <asp:Label ID="email_Label" runat="server" Text="Email"></asp:Label>
                </td>
                <td>
                    <asp:TextBox ID="email_TextBox" runat="server" CssClass="fields"></asp:TextBox>
                    <br />
                    <asp:RequiredFieldValidator ID="RequiredFieldValidator4" runat="server" ControlToValidate="email_TextBox"
                        Display="Dynamic" ErrorMessage="Email is required field"></asp:RequiredFieldValidator>
                    <asp:RegularExpressionValidator ID="RegularExpressionValidator1" runat="server" ControlToValidate="email_TextBox"
                        Display="Dynamic" ErrorMessage="Email isn't valid." ValidationExpression="\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*"></asp:RegularExpressionValidator>
                </td>
            </tr>
        </table>
        <br />
        <br />
        <asp:Button ID="submit_Button" runat="server" OnClick="submit_Button_Click" 
            Text="Submit" CssClass="button" />
            </asp:Panel>
    </div>
</asp:Content>
