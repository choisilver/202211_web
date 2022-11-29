/**
 * member.js
 * /member/signup.html
 */
 
 window.addEventListener('DOMContentLoaded', function() {
    // 아이티 중복체크
    
    const usernameInput = document.querySelector('#username');  // 아이디 가져오기 
    const okDiv = document.querySelector('#ok');
    const nokDiv = document.querySelector('#nok');
    const btnSubmit = document.querySelector('#btnSubmit');
    
    usernameInput.addEventListener('change', function(){
        const username = usernameInput.value;
        axios
        .get('/member/checkid?username='+ username)  // GET Ajax 요청 (주소가 바뀌지 않음)
        .then(response => { displayCheckResult(response.data) })  // 성공(HTTP 200) 응답 콜백
        .catch(err => {console.log(err); }); // 실패 응답 콜백
    }); // 입력 내용이 바뀔 때
    
    function displayCheckResult(data) {
        if (data =='ok'){ // 사용할 수 있는 아이디
            okDiv.className = 'my-2';
            nokDiv.className = 'my-2 d-none';
            btnSubmit.classList.remove('disabled'); // 버튼 활성화
            
        } else { // 사용할 수 없는 아이디
            okDiv.className = 'my-2 d-none';
            nokDiv.className = 'my-2';
            btnSubmit.classList.add('disabled'); //  버튼 비활성화
        }
        
    }    
    
    
    
});
 
 
 
 
 