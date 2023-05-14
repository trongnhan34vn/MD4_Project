


   
function editBtn() {
    let editBtn = document.getElementsByClassName('btn-modal-s')
    
    for (let i = 0; i < editBtn.length; i++) {
        editBtn[i].addEventListener('click', function() {
            document.getElementById('overlay').classList.add('is-visible');
            document.getElementById('modal').classList.add('is-visible');
          });
    }
}

editBtn()

function closeBtn() {
    document.getElementById('close-btn').addEventListener('click', function() {
        console.log("aaaaaaa");
        document.getElementById('overlay').classList.remove('is-visible');
        document.getElementById('modal').classList.remove('is-visible');
      });
      document.getElementById('overlay').addEventListener('click', function() {
        console.log("bbbbbbbb");
        document.getElementById('overlay').classList.remove('is-visible');
        document.getElementById('modal').classList.remove('is-visible');
      });
}

closeBtn()


  