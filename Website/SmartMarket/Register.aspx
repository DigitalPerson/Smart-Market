<%@ Page Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true"
    CodeFile="Register.aspx.cs" Inherits="Register" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head_ContentPlaceHolder" runat="Server">
    <title>Register</title>
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
                    <asp:Label ID="firstName_Label" runat="server" Text="First Name"></asp:Label>
                </td>
                <td>
                    <asp:TextBox ID="firstName_TextBox" runat="server" CssClass="fields"></asp:TextBox>
                    <br />
                    <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" ControlToValidate="firstName_TextBox"
                        Display="Dynamic" ErrorMessage="First name is required field."></asp:RequiredFieldValidator>
                </td>
            </tr>
            <tr>
                <td class="style1">
                    <asp:Label ID="lastName_Label" runat="server" Text="Last Name"></asp:Label>
                </td>
                <td>
                    <asp:TextBox ID="lastName_TextBox" runat="server" CssClass="fields"></asp:TextBox>
                    <br />
                    <asp:RequiredFieldValidator ID="RequiredFieldValidator2" runat="server" ControlToValidate="lastName_TextBox"
                        Display="Dynamic" ErrorMessage="Last name is required field"></asp:RequiredFieldValidator>
                </td>
            </tr>
            <tr>
                <td class="style1">
                    <asp:Label ID="gender_Label" runat="server" Text="Gender"></asp:Label>
                </td>
                <td>
                    <asp:DropDownList ID="gender_DropDownList" runat="server" CssClass="fields">
                        <asp:ListItem Value="m">Male</asp:ListItem>
                        <asp:ListItem Value="f">Female</asp:ListItem>
                    </asp:DropDownList>
                </td>
            </tr>
            <tr>
                <td class="style1">
                    <asp:Label ID="country_Label" runat="server" Text="Country"></asp:Label>
                </td>
                <td>
                    <asp:DropDownList ID="country_DropDownList" runat="server" DataSourceID="countries_LinqDataSource"
                        DataTextField="name" DataValueField="countryID" CssClass="fields">
                    </asp:DropDownList>
                    <asp:LinqDataSource ID="countries_LinqDataSource" runat="server" ContextTypeName="SmartMarketDataClassesDataContext"
                        EntityTypeName="" TableName="Countries">
                    </asp:LinqDataSource>
                </td>
            </tr>
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
            <tr>
                <td class="style1">
                    <asp:Label ID="password_Label" runat="server" Text="Password"></asp:Label>
                </td>
                <td>
                    <asp:TextBox ID="password_TextBox" runat="server" TextMode="Password" CssClass="fields"></asp:TextBox>
                    <br />
                    <asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server" ControlToValidate="password_TextBox"
                        Display="Dynamic" ErrorMessage="Password is required field"></asp:RequiredFieldValidator>
                    <asp:RegularExpressionValidator ID="RegularExpressionValidator2" runat="server" ControlToValidate="password_TextBox"
                        Display="Dynamic" ErrorMessage="Password must be at least 8 characters." ValidationExpression="^.{8,}$"></asp:RegularExpressionValidator>
                </td>
            </tr>
            <tr>
                <td class="style1">
                    <asp:Label ID="confirmPassword_Label" runat="server" Text="Confirm Password"></asp:Label>
                </td>
                <td>
                    <asp:TextBox ID="confirmPassword_TextBox" runat="server" TextMode="Password" CssClass="fields"></asp:TextBox>
                    <br />
                    <asp:CompareValidator ID="CompareValidator1" runat="server" ControlToCompare="password_TextBox"
                        ControlToValidate="confirmPassword_TextBox" Display="Dynamic" ErrorMessage="These passwords don't match. Try again?"></asp:CompareValidator>
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
