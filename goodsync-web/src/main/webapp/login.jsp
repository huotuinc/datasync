<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>jjj</title>
    <style type="text/css">
        /* css reset */
        body {
            color: #000;
            background: #fff;
            font-size: 12px;
            line-height: 166.6%;
            text-align: center;
        }

        body.move {
            -webkit-transition: padding 0.3s ease;
            -moz-transition: padding 0.3s ease;
            -o-transition: padding 0.3s ease;
            -ms-transition: padding 0.3s ease;
            transition: padding 0.3s ease;
        }

        body, input, select, button {
            font-family: "微软雅黑", verdana;
        }

        h1, h2, h3, select, input, button {
            font-size: 100%;
        }

        body, h1, h2, h3, ul, li, form, p, img {
            margin: 0;
            padding: 0;
            border: 0;
        }

        input, button, select, img {
            margin: 0;
            line-height: normal;
        }

        select {
            padding: 1px;
        }

        ul {
            list-style: none;
        }

        select, input, button, button img, label {
            vertical-align: middle;
        }

        header, footer, section, aside, nav, hgroup, figure, figcaption {
            display: block;
            margin: 0;
            padding: 0;
            border: none;
        }

        a {
            text-decoration: none;
            color: #959595;
        }

        a:hover {
            color: #626262;
        }

        .fontWeight {
            font-weight: 700;
        }

        /* global */
        .unvisi {
            visibility: hidden;
        }

        .txt-suc {
            color: #22AC38;
        }

        .txt-err {
            color: #e60012;
        }

        .txt-yixin {
            color: #279C7B;
        }

        /* backgroundImage */
        .themeCtrl a, .headerIntro, .loginIcoCurrent, .loginIcoNew, .verify-input-line, .themeText li, .btn, .btn-moblogin, .ico, .locationTestTitle, .verSelected, .servSelected, .locationTestTitleClose, #extText li, #mobtips_arr, #mobtips_close {
            /*background-image: url(/Content/images/dl/bg_v3.png);*/
        }

        .headerLogo, .headerIntro, .headerNav, .footerLogo, .footerNav, .loginIcoCurrent, .loginIcoNew, .formIpt, .domain, #whatAutologinTip, #mobtips, #mobtips_arr, #mobtips_close {
            position: absolute;
        }

        /* yixin */
        #appLoginEntry, #closeappLogin, .ico-appLogo, .loginByapp-refreshBtn {
            background-image: url(/Content/images/dl/app_ico.png);
            background-repeat: no-repeat;
        }

        /* ico */
        .ico-uid {
            width: 14px;
            height: 16px;
            background-position: -154px -64px;
        }

        .ico-pwd {
            width: 14px;
            height: 16px;
            background-position: -178px -64px;
        }

        .ico-miniAlert {
            margin-right: 3px;
            display: inline-block;
            width: 14px;
            height: 14px;
            background-position: -132px -112px;
            vertical-align: top;
        }

        .ico-arr {
            display: inline-block;
            width: 7px;
            height: 12px;
            vertical-align: baseline;
            background-position: -160px -112px;
        }

        .ico-arr-d {
            background-position: -160px -110px;
        }

        .loginFormConf a:hover .ico-arr-d, .ico-arr-d-focus {
            background-position: -176px -110px;
        }

        * + html .ico-arr-d {
            background-position: -160px -112px;
        }

        * + html .loginFormConf a:hover .ico-arr-d, * + html .loginFormConf a:hover .ico-arr-d, * + html .ico-arr-d-focus {
            background-position: -176px -112px;
        }

        .ico-appLogo {
            display: inline-block;
            margin-top: -3px;
            _margin-top: 0;
            width: 19px;
            height: 19px;
            background-position: -90px 0;
            vertical-align: middle;
        }

        /* header */
        .header {
            width: 1000px;
            height: 121px;
            position: relative;
            margin: 0 auto;
            z-index: 2;
            overflow: hidden;
        }

        .headerIntro {
            height: 28px;
            width: 144px;
            display: block;
            background-position: 0 -64px;
            top: 17px;
            left: 194px;
        }

        .headerNav {
            top: 21px;
            text-align: right;
            color: #FFFFFF;
            font-size: 14px;
            background-color: #fff;
            border-radius: 8px;
            padding: 2px 15px;
        }

        .headerNav a {
            padding: 0px 6px;
            display: inline-block;
            color: #000;
        }

        .headerNav .last {
            padding-left: 0;
        }

        /* main */
        .main {
            height: 611px;
            background: #fff;
            position: relative;
            min-width: 1000px;
        }

        .main-inner-iframe {
            border: 0;
            width: 100%;
            height: 600px;
            overflow: hidden;
        }

        #mainCnt {
            width: 100%;
            height: 600px;
            position: relative;
            clear: both;
            background-repeat: no-repeat;
            background-position: center top;
        }

        #theme {
            margin: 0 auto;
            height: 600px;
            width: 1000px;
            overflow: hidden;
            position: relative;
        }

        .themeLink {
            height: 274px;
            width: 430px;
            display: block;
            outline: 0;
        }

        .themeText {
            margin-left: 26px;
        }

        .themeText li {
            line-height: 22px;
            -line-height: 24px;
            height: 24px;
            color: #858686;
            text-indent: 12px;
            background-position: -756px -72px;
            background-repeat: no-repeat;
        }

        .themeText li a {
            color: #005590;
            text-decoration: underline;
        }

        .login {
            width: 295px;
            height: 460px;
            padding: 13px 14px 15px;
            top: 0px;
            left: 63%;
            text-align: left;
            position: absolute;
            z-index: 2;
            background: url(/Content/images/dl/login_v4.png) no-repeat;
            -background-image: url(/Content/images/dl/login_ie6_v4.png);
        }

        .unishadow {
            box-shadow: 0px 1px 3px 0 rgba(0, 0, 0, 0.2);
            -webkit-box-shadow: 0px 1px 3px 0 rgba(0, 0, 0, 0.2);
            -moz-box-shadow: 0px 1px 3px 0 rgba(0, 0, 0, 0.2);
        }

        .loginFunc {
            width: 100%;
            height: 40px;
            overflow: hidden;
            clear: both;
            padding-top: 20px;
        }

        .loginFuncNormal, .loginFuncDynPw {
            width: 148px;
            height: 100%;
            overflow: hidden;
            position: relative;
            line-height: 46px;
            float: left;
            font-size: 14px;
            text-align: center;
        + line-height : 48 px;
            color: #626262;
            cursor: pointer;
        }

        .loginFuncDynPw {
            width: 147px;
            border-right: none;
        }

        .loginIcoCurrent {
            width: 24px;
            height: 24px;
            left: 26px;
            top: 9px;
            display: none;
        }

        .loginIcoNew {
            width: 21px;
            height: 10px;
            font-size: 0;
            background-position: -684px 0;
            left: 135px;
            top: 12px;
        }

        .tab-2 {
            background-position: -323px 0;
        }

        .tab-2 .loginFuncDynPw .loginIcoCurrent, .tab-1 .loginFuncNormal .loginIcoCurrent, .tab-11 #extVerSelect, .tab-1 #lfBtnReg1 {
            display: block;
        }

        .tab-11 #extText, .tab-2 #lfBtnReg {
            display: none;
        }

        /* form */
        .loginForm {
            position: relative;
            padding-top: 13px;
        }

        .loginFormIpt showPlaceholder {
            position: relative;
            width: 360px;
            height: 42px;
            line-height: 42px;
            margin: 0 0 10px 25px;
            padding: 10px;
            clear: both;
            z-index: 2;
            background: rgba(255, 255, 255, 0.4);
            border: solid 1px #D8D8D8;
        }

        .loginFormIpt {
            position: relative;
            width: 360px;
            height: 44px;
            line-height: 42px;
            margin: 0 0 10px 25px;
            padding: 10px;
            clear: both;
            z-index: 2;
        }

        .loginFormIpt .ico {
            position: absolute;
            left: 9px;
            top: 13px;
            z-index: 1;
        }

        .loginFormIpt-over {
        }

        .loginFormIpt-focus {
            box-shadow: 0 0 5px rgba(2, 145, 252, .5);
        }

        .loginFormIpt-focus .placeholder {
            color: #C9C9C9;
        }

        .loginFormBtn {
            position: relative;
            width: 245px;
            height: 38px;
            margin: 18px 0 0 25px;
        }

        .formIpt {
            position: relative;
            width: 330px;
            height: 21px;
            line-height: 21px;
            padding: 10px;
            clear: both;
            z-index: 2;
            background: rgba(255, 255, 255, 0.4);
            border: solid 1px #D8D8D8;
        }

        .formIpt:focus {
            outline: 0;
        }

        .showPlaceholder .placeholder {
            visibility: visible;
            cursor: text;
        }

        .placeholder {
            color: #C9C9C9;;
            font-size: 14px;
            position: absolute;
            left: 30px;
            top: 14px;
            line-height: 14px;
            visibility: hidden;
            background: none;
        }

        .domain {
            padding-left: 5px;
            width: 75px;
            height: 33px;
            line-height: 33px;
            color: #616161;
            font-size: 14px;
            overflow: hidden;
            display: block;
            right: 8px;
            top: 4px;
            white-space: nowrap;
        }

        #idInput::-ms-clear {
            display: none;
        }

        #idInputTest {
            visibility: hidden;
            position: absolute;
            font-size: 14px;
            font-weight: 700;
        }

        .loginFormCheck {
            height: 14px;
            line-height: 14px;
            color: #555;
            margin-left: 25px;
            clear: both;
            width: 245px;
            position: relative;
            z-index: 1;
        }

        .loginFormCheckInner {
            line-height: 13px;
            width: 150px;
            float: left;
            position: relative;
        }

        #dynPwInput {
            width: 110px;
        }

        #dynPwLoginHint {
            margin-left: 25px;
            height: 14px;
            line-height: 14px;
            color: #959595;
        }

        .forgetPwdLine {
            text-align: right;
        }

        #capsLockHint {
            position: absolute;
            top: 42px;
            left: 0px;
            padding: 4px 8px;
            line-height: 12px;
            background-color: #ffffcc;
            border: 1px solid #d7d7d7;
            color: #555;
            z-index: 2;
        }

        #remAutoLogin {
            visibility: hidden;
            position: absolute;
            left: 0;
        }

        .ico-checkbox {
            display: inline-block;
            width: 13px;
            height: 13px;
            background-position: -40px -160px;
            vertical-align: middle;
            cursor: pointer;
        }

        .autoLogin-checked .ico-checkbox {
            background-position: -40px -180px;
        }

        #remAutoLoginTxt, .forgetPwd {
            color: #848585;
        }

        #remAutoLoginTxt:hover, .forgetPwd:hover {
            color: #626262;
        }

        .loginFormCbx {
            width: 13px;
            height: 13px;
            padding: 0;
            overflow: hidden;
            margin: 0;
            vertical-align: middle;
        }

        #whatAutologinTip {
            z-index: 9;
            width: 180px;
            height: 36px;
            background-color: #fffde4;
            border: 1px #dfb86d solid;
            left: 0px;
            top: 16px;
            text-align: left;
            padding: 5px 10px;
            line-height: 18px;
            color: #dc9632;
            display: none;
            border-radius: 4px;
        }

        .btn {
            width: 110px;
            height: 38px;
            float: left;
            text-align: center;
            cursor: pointer;
            border: 0;
            padding: 0;
            font-weight: 700;
            font-size: 14px;
            display: inline-block;
            vertical-align: baseline;
            line-height: 38px;
            outline: 0;
            background-color: transparent;
        }

        .btn-main {
            color: #fff;
            box-shadow: 0 2px 5px rgba(0, 28, 88, .3);
        }

        .btn-side {
            color: #6d798c;
            box-shadow: 0 2px 5px rgba(0, 0, 0, .1);
        }

        .btn-login {
            background-position: 0 -208px;
        }

        .btn-login-hover {
            background-position: 0 -256px;
        }

        .btn-login-active {
            background-position: 0 -304px;
            color: #b5d1ee;
        }

        .btn-reg {
            background-position: -117px -208px;
            float: right;
        }

        .btn-reg-hover {
            background-position: -117px -256px;
            color: #347bc7;
        }

        .btn-reg-active {
            background-position: -117px -304px;
            color: #6d798c;
        }

        .btn-login2 {
            width: 245px;
            height: 38px;
            background-position: 0 -710px;
        }

        .btn-login2-hover {
            background-position: 0 -758px;
        }

        .btn-login2-active {
            background-position: 0 -806px;
            color: #b5d1ee;
        }

        .btn-showDynPwWrap {
            position: absolute;
            top: 0;
            right: 0;
            _right: -1px;
            display: block;
            width: 101px;
            height: 42px;
            font-size: 14px;
            font-weight: normal;
            text-align: center;
            line-height: 42px;
            color: #6d798c;
            background-position: -195px -405px;
        }

        .btn-showDynPwWrap-hover {
            color: #347bc7;
            background-position: -195px -455px;
        }

        .btn-showDynPwWrap-active {
            color: #6d798c;
            background-position: -195px -505px;
        }

        .btn-showDynPwWrapimg {
            position: absolute;
            top: 0;
            right: 0;
            _right: -1px;
            display: block;
            width: 119px;
            height: 42px;
            font-size: 14px;
            font-weight: normal;
            text-align: center;
            color: #6d798c;
            background-position: -115px -157px;
            margin-right: 14px;
            margin-top: 12px;
            z-index: 10000;
        }

        .btn-getDynPw {
            width: 150px;
            background-position: 0 -565px;
        }

        .btn-getDynPw-hover {
            background-position: 0 -613px;
        }

        .btn-getDynPw-active {
            background-position: 0 -661px;
        }

        .btn-cancel {
            width: 124px;
            background-position: -160px -565px;
        }

        .btn-cancel-hover {
            background-position: -160px -613px;
            color: #347bc7;
        }

        .btn-cancel-active {
            background-position: -160px -661px;
            color: #6d798c;
        }

        .loginFormConf {
            height: 14px;
            line-height: 14px;
            margin-left: 25px;
            margin-top: 18px;
            clear: both;
            width: 245px;
            position: relative;
            color: #848585;
            z-index: 1;
        }

        .loginFormVer {
            float: left;
            width: 160px;
        }

        .loginFormService {
            float: right;
            text-align: right;
        }

        .loginFormVerList {
            width: 140px;
            position: absolute;
            padding: 1px;
            background: #fff;
            border: 1px solid #b7c2c9;
            top: -5px;
            top: -4px \9;
            left: 33px;
            display: none;
        }

        .loginFormVerList li a {
            height: 22px;
            line-height: 22px;
            width: 140px;
            overflow: hidden;
            color: #848585;
            display: block;
            text-indent: 22px;
        }

        .loginFormVerList li a:hover {
            background-color: #eef3f8;
        }

        .loginFormVerList li a.verSelected {
            color: #5B8CCA;
            background-position: -250px -58px;
            background-repeat: no-repeat;
        }

        /* app */
        .mailApp {
            margin: 30px 0 0 20px;
        }

        .mailApp-logo {
            display: block;
            width: 256px;
            height: 50px;
            background: url(images/dl/mailapp_logo.gif);
            _background-image: url(/Content/iamges/mailapp_logo.gif);
        }

        /* ext */
        #extVerSelect {
            display: none;
        }

        .ext {
            padding: 13px 55px 13px 25px;
            height: 38px;
            overflow: hidden;
        }

        #extText {
            line-height: 20px;
        }

        #extText li {
            padding-left: 7px;
            background-position: -240px -118px;
            background-repeat: no-repeat;
        }

        #extText li a {
            color: #9bacc6;
        }

        #extText li a:hover {
            color: #5B8CCA;
        }

        #extVerSelect {
            line-height: 40px;
            font-size: 12px;
            font-weight: 700;
        }

        #extVerSelect a {
            color: #005590;
            text-decoration: underline;
        }

        /* tab-2 */
        .tab-2 .ico-mob {
            top: 12px;
            width: 13px;
            height: 18px;
            background-position: -200px -64px;
        }

        /* footer */
        .footer {
            height: 65px;
            margin: 0 auto;
        }

        .footer-inner {
            width: 1200px;
            height: 63px;
            overflow: visible;
            margin: 0 auto;
            color: #848585;
            position: relative;
        }

        .footerLogo {
            top: 11px;
            left: 35px;
        }

        .footerNav {
            top: 25px;
            right: 0px;
            left: 0px;
        }

        .footerNav a {
        }

        .copyright {
        }

        /* noscript */
        .noscriptTitle {
            line-height: 32px;
            font-size: 24px;
            color: #d90000;
            padding-top: 60px;
            font-weight: 700;
            background: #fff;
        }

        .noscriptLink {
            text-decoration: underline;
            color: #005590;
            font-size: 14px;
        }

        /* mobtips */
        #mobtips {
            height: 18px;
            border: 1px solid #c6c6a8;
            top: 41px;
            left: 30px;
            line-height: 18px;
            background: #ffffe1;
            padding-left: 6px;
            padding-right: 20px;
            display: none;
            color: #565656;
            zoom: 1;
        }

        #mobtips_arr {
            width: 9px;
            height: 9px;
            background-position: -684px -72px;
            top: -5px;
            left: 15px;
        }

        #mobtips_close {
            background-position: -715px -68px;
            top: 2px;
            width: 16px;
            height: 14px;
            right: 0px;
        }

        #mobtips em {
            font-style: normal;
            color: #328721;
        }

        #mobtips a {
            text-decoration: underline;
            color: #005590;
        }

        /* mask */
        .mask {
            position: absolute;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background: #000;
            filter: alpha(opacity=70);
            -moz-opacity: 0.7;
            opacity: 0.7;
            z-index: 998;
        }

        /* 加密http登录弹窗 */
        .enhttp .topborder, .enhttp .bottomborder, .enhttp .ct, .enhttp .cldenhttp, .enhttp .ct .inner .httplogin {
            background-image: url(/Content/images/dl/bg_httplogin.gif);
            background-color: transparent;
            background-repeat: no-repeat;
            text-decoration: none;
        }

        .enhttp {
            width: 420px;
            height: 270px;
            position: absolute;
            z-index: 999;
            overflow: hidden;
            top: 0;
            left: 50%;
            margin-left: -210px;
            top: 50%;
            margin-top: -135px;
        }

        .enhttp .topborder {
            width: 418px;
            height: 2px;
            font-size: 1px;
            overflow: hidden;
            margin: 0 auto;
            background-position: 0 -108px;
        }

        .enhttp .bottomborder {
            width: 418px;
            height: 2px;
            font-size: 1px;
            overflow: hidden;
            margin: 0 auto;
            background-position: 0 -110px;
        }

        .enhttp .ct {
            width: 418px;
            height: 266px;
            background-position: 0 -134px;
            background-color: #fff;
            border-left: 1px solid #82aecd;
            border-right: 1px solid #82aecd;
            position: relative;
            overflow: hidden;
        }

        .enhttp .ct .inner {
            padding-top: 40px;
            margin: 0 auto;
            text-align: left;
        }

        .enhttp .ct .inner p {
            font-size: 14px;
        }

        .enhttp .ct .inner .txt-tips {
            color: #737373;
            line-height: 30px;
            width: 325px;
            margin-left: 46px;
            display: inline;
        }

        .enhttp .ct .inner .txt-normal {
            line-height: 30px;
            width: 325px;
            margin: 10px 0 0 46px;
        }

        .enhttp .ct .inner .httplogin {
            font-size: 14px;
            height: 34px;
            width: 120px;
            display: block;
            background-position: -432px -108px;
            line-height: 34px;
            text-align: center;
            color: #fff;
            font-weight: 700;
        }

        .enhttp .ct .inner .txt-line {
            width: 325px;
            margin-left: 46px;
            background: #b6cad9;
            height: 1px;
            overflow: hidden;
            font-size: 1px;
            margin-top: 24px;
        }

        .enhttp .ct .inner .txt-advice {
            line-height: 60px;
            width: 325px;
            color: #8d8d8d;
            margin-left: 46px;
        }

        .enhttp .ct .inner .txt-advicelink {
            margin-left: 20px;
            font-size: 14px;
        }

        .enhttp .cldenhttp {
            height: 22px;
            width: 22px;
            overflow: hidden;
            position: absolute;
            right: 8px;
            top: 6px;
            background-position: 0 -112px;
            text-indent: -9999px;
        }

        .enhttp .cldenhttp:hover {
            background-position: -22px -112px;
        }

        .enhttp .enhttpbox {
            position: absolute;
            z-index: 2;
            left: 0;
        }

        .enhttp .httploginframe {
            width: 100%;
            height: 200px;
            position: absolute;
            top: 2px;
            z-index: 1;
            left: 0;
        }

        /* 测速 */
        #locationTest {
            position: absolute;
            width: 255px;
            top: -2px;
            left: 0px;
            height: 88px;
            background: #fff;
            border: 1px solid #b7c2c9;
            display:;
            margin-bottom: 200px;
            height: 79px;
            overflow: hidden;
            display: none;
        }

        .locationTestTitle {
            width: 255px;
            height: 26px;
            line-height: 26px;
            position: relative;
            color: #555;
            text-indent: 10px;
            background-position: 0 -10px;
            border-bottom: 1px solid #f1f3f5;
        }

        .locationTestTitle h3 {
            font-size: 12px;
        }

        .locationTestTitleClose {
            height: 8px;
            width: 8px;
            overflow: hidden;
            display: block;
            position: absolute;
            right: 6px;
            top: 7px;
            background-position: -224px -112px;
        }

        .locationTestTitleClose:hover {
            background-position: -208px -112px;
        }

        .locationTestEach {
            display: inline-block;
            width: 5em;
            font-family: verdana;
            color: #848585;
        }

        .locationTestList li {
            padding: 2px;
            float: left;
            display: inline-block;
        }

        .locationTestList .servSelected {
            background-position: -248px -50px;
            background-repeat: no-repeat;
        }

        .locationTestList li a {
            height: 38px;
            width: 80px;
            display: block;
            line-height: 16px;
            padding-top: 10px;
            overflow: hidden;
            text-align: center;
            color: #000;
        }

        .locationTestList li a:hover {
            background-color: #eef3f8;
        }

        #selectLocation {
            text-align: center;
        }

        #locationTestCur {
            width: 3em;
        }

        #selectLocationTipsDone {
            display: none;
        }

        .locationTestBest {
            display: none;
            color: green;
        }

        .locationChoose {
            text-decoration: underline;
            color: #005590;
        }

        /* ie6 */
        #musicLink, #musicLink:hover, #prevTheme, #prevTheme:hover, #nextTheme, #nextTheme:hover, #scoreIndex, #scoreIndex:hover {
            -height: 24px;
            -background-position-y: -527px;
        }

        .verify {
            padding: 10px;
        }

        .verify-wrap {
            height: 70px;
        }

        .verify-tt {
            margin-bottom: 10px;
            font-size: 14px;
            font-weight: bold;
        }

        .verify-input {
            float: left;
        }

        .verify-img {
            margin-left: 156px;
        }

        .verify-input-line {
            width: 150px;
            height: 42px;
            background-position: 0 -405px;
            position: relative;
        }

        .verify-input-line .formIpt {
            left: 7px;
            width: 135px;
        }

        .verify-img-btn {
            float: right;
            color: #4E90E2;
            line-height: 22px;
        }

        .verify-img-btn:hover {
            color: #4E90E2;
            text-decoration: underline;
        }

        .verify-img-btn img {
            vertical-align: top;
        }

        .verify .btn-getDynPw {
            margin-right: 12px;
        }

        .verify-opt {
            height: 42px;
        }

        .verify-err {
            line-height: 22px;
        }

        #appLoginEntry, #closeappLogin {
            position: absolute;
            display: block;
            width: 64px;
            height: 64px;
        }

        #appLoginEntry {
            background-position: 0 -75px;
            right: 14px;
            bottom: 15px;
            _right: 13px;
            _bottom: 14px;
        }

        #appLoginEntry:hover {
            background-position: -65px -75px;
        }

        .loginFormIpt select {
            border: 0px;
            font-size: 16px;
            text-align: center;
            width: 98%;
            margin: 0px 5px;
            height: 44px;
            color: #868686;
            vertical-align: central;
            background: rgba(255, 255, 255, 0.4);
            border: solid 1px #D8D8D8;
        }

        .buttons3 {
            background-color: rgba(255, 255, 255, 0.4);
            text-align: center;
            padding: 6px 5px;
            margin: 0px 5px;
            width: 80%;
            color: #222;
            font-size: 16PX;
            border: 1px #464646 solid;
            font-family: "微软雅黑";
        }

        .buttons3:active {
            background-color: rgba(255, 255, 255, 0.5);
            text-align: center;
            padding: 6px 5px;
            margin: 0px 5px;
            width: 80%;
            color: #222;
            font-size: 16PX;
            border: 1px #464646 solid;
            font-family: "微软雅黑";
        }
    </style>
    <script type="text/javascript" src="/resources/scripts/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="/resources/scripts/jquery.utils.js"></script>
    <script type="text/javascript" src="<c:url value="/resources/scripts/jBox/jquery.jBox-2.3.min.js" />"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/scripts/jBox/Skins/Green/jbox.css"/>">
    <script type="text/javascript">
        var ajaxUrl = "<c:url value="/login" />";
        $(function () {
            $("#loginBtn").click(function () {
                var account = $.trim($("#account").val());
                var password = $.trim($("#password").val());
                var requestData = {
                    account: account,
                    password: password
                };
                J.GetJsonRespons(ajaxUrl, requestData, function (json) {
                    if (json == 1) {
                        $.jBox.tip("登录成功，正在跳转..", "loading");
                        window.location.href = "<c:url value="/index" />";
                    } else {
                        $.jBox.tip("登录失败，请检查用户名密码");
                    }
                }, function () {
                }, J.PostMethod);
            })
        });
    </script>
</head>
<body style="zoom: 1; background-color: #292929;" class=" move">
<div style="width: 100%; padding-top: 20px; background-color: #e8ecef;">
    <header class="header">
        <nav class="headerNav">
            <a target="_blank" href="http://www.huobanplus.com/">首页</a>
            &nbsp;<a target="_blank" href="http://www.huobanplus.com/index2.html">产品说明</a>
            @* &nbsp;<a target="_blank" href="http://bbs.huobanmall.com">商家社区</a>*@
        </nav>
    </header>
</div>
<section class="main" style="background: url(/resources/images/muban.jpg) center center;">
    <!--登录框-->
    <div>
        <img src="/resources/images/huobanshangchenglogo.png" width="15%">

        <form style="height: 335px;">
            <div class="loginFormIpt showPlaceholder" style="text-align: center; margin: 0 auto;">
                <input class="formIpt" tabindex="1" title="请输入帐号" id="account" type="text" maxlength="50"
                       autocomplete="off" placeholder="帐号">
            </div>

            <div class="loginFormIpt showPlaceholder" style="text-align: center; margin: 0 auto;">

                <input class="formIpt" tabindex="2" title="请输入密码" id="password" type="password" placeholder="密码">
            </div>
            <div style="text-align: center; margin: 0 auto; width: 389px; padding-top: 10px;">

                <p style="width: 50%;margin:0 auto">
                    <input id="loginBtn" type="button" class="buttons3" value="登&nbsp;&nbsp;录">
                </p>


            </div>


            <div style="width: 7%; position: fixed; top: 60px; margin-left: 88%;">
                <img src="/resources/images/or.jpg" width="100%">
            </div>
        </form>
    </div>


</section>
<footer id="footer" class="footer">
    <div class="footer-inner" id="footerInner">


        <nav class="footerNav">

            <span class="copyright"></span>
        </nav>
    </div>
</footer>
</body>
</html>
