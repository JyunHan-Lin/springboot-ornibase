document.addEventListener("DOMContentLoaded", function() {
  // 行為紀錄 Ajax
  const behaviorForm = document.getElementById("behaviorForm");
  behaviorForm.addEventListener("submit", function(e) {
    e.preventDefault(); // 阻止預設表單送出

    const formData = new FormData(behaviorForm);
    const url = behaviorForm.getAttribute("action");

    fetch(url, {
      method: "POST",
      body: formData,
      headers: {
        "X-Requested-With": "XMLHttpRequest"  // 告訴後端是Ajax請求(常用)
      }
    })
    .then(resp => {
      if (!resp.ok) throw new Error("網路錯誤");
      return resp.json();  // 假設後端回傳 JSON
    })
    .then(data => {
      behaviorForm.reset();  // 清空表單
      // 更新行為清單或圖表
	  loadTimelineData();
	  drawFoodChart();
    })
    .catch(err => alert("送出失敗：" + err));
  });

  // 留言 Ajax
  const commentForm = document.getElementById("commentForm");
  const commentTextarea = document.querySelector('textarea[name="content"]');
  
  commentForm.addEventListener("submit", function(e) {
    e.preventDefault();

    const formData = new FormData(commentForm);
    const url = commentForm.action;

    fetch(url, {
      method: "POST",
      body: formData,
      headers: {
        "X-Requested-With": "XMLHttpRequest"
      }
    })
    .then(resp => {
      if (!resp.ok) throw new Error("網路錯誤");
      return resp.json();  // 後端回 JSON
    })
    .then(data => {
      commentForm.reset();
      appendCommentToList(data.comment);
    })
    .catch(err => alert("留言送出失敗：" + err));
  });

  commentTextarea.addEventListener("keydown", function(e) {
  if (e.key === "Enter" && !e.shiftKey) { // 按 Enter 且沒按 Shift
      e.preventDefault(); // 防止換行
      commentForm.dispatchEvent(new Event("submit")); // 觸發送出事件
	  }
  });
  // 將留言加入留言清單區（範例）
  function appendCommentToList(comment) {
    const commentList = document.querySelector(".comment-list");
    const div = document.createElement("div");
    div.classList.add("comment-row");
    div.innerHTML = `
      <div class="comment-left">
        <div class="comment-avatar" title="${comment.userName}">
          ${comment.userName.charAt(0)}
        </div>
        <div class="comment-content">
          <span class="comment-user">${comment.userName}</span>
          <p class="comment-text">${comment.content}</p>
        </div>
      </div>
      <div class="comment-time">${comment.formattedCreatedTime}</div>
    `;
    commentList.appendChild(div);
	// 捲動到最新留言
	div.scrollIntoView({ behavior: "auto", block: "end" });
  }
});



