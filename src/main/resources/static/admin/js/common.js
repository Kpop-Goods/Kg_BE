window.addEventListener("DOMContentLoaded", function() {
    /* 체크박스 전체 선택 처리 방식*/
    const checkalls = document.getElementsByClassName("checkall");
    for (const el of checkalls) {
        el.addEventListener("click", function () {
            const name = this.dataset.targetName;
            const chks = document.getElementsByName(name);
            for (const el of chks) {
                el.checked = this.checked;
            }
        });
    }

});
