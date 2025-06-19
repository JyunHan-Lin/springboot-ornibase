document.addEventListener("DOMContentLoaded", function () {
    const params = new URLSearchParams(window.location.search);
    const status = params.get("status");

    if (status === "success") {
        alert("註冊成功！ 記得到信箱認證開啟帳號");
    } else if (status === "fail") {
        alert("註冊失敗，請檢查資料是否正確或帳號是否已存在。");
    }
});
