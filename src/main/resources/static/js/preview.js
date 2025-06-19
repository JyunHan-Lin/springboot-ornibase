  document.addEventListener("DOMContentLoaded", function () {
  // 如果使用者未登入，才加阻擋功能
  if (window.notLoggedIn) {
    // 阻擋影片卡片點擊
    document.querySelectorAll('.video-card').forEach(function (card) {
      card.addEventListener('click', function (event) {
        event.preventDefault();
        alert("必須先登入才能檢視討論串！");
        window.location.href = "/login";
      });
    });

   // 阻擋導覽列：建立筆記本 / 書架
   document.querySelectorAll('.nav-list .emphasis').forEach(function (link) {
   	link.addEventListener('click', function (event) {
        event.preventDefault();
        alert("必須先登入才能使用此功能！");
        window.location.href = "/login";
      });
    });
  }
});