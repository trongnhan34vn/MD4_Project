window.onload = function()  {
    let checkCurrentUser = JSON.parse(localStorage.getItem('current_user'));
    if(checkCurrentUser == null) {
        alert("Bạn chưa đăng nhập!");
        location.href = "./login.html";
    } 
}