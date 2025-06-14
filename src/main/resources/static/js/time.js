
const startTimeInput = document.getElementById("startTime");
const endTimeInput = document.getElementById("endTime");

startTimeInput.addEventListener("change", function () {
if (startTimeInput.value) {
	endTimeInput.focus();  // 自動跳到下一欄
}
});

