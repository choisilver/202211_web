/**
 * reply.js
 * 댓글 관련 Ajax 기능 구현
 * /post/detail.html에 포함됨.
 */
 window.addEventListener('DOMContentLoaded', () => { // 람다표현식 사용할 수 있음. function(event), event =>
    // HTML의 Document Object들이 모두 로딩이 끝난 후에 코드들이 실행될 수 있도록 하기 위해서. 자바스크립트가 요상한 곳에 있을 수 있음을 방지하기 위해.
    readAllReplies(); // 포스트 상세 페이지가 로딩 된 후 댓글 목록을 볼 수 있음.
    // btnReplyRegister
    const btnReplyRegister = document.querySelector('#btnReplyRegister');
    btnReplyRegister.addEventListener('click', registerNewReply);
    
    function registerNewReply() { // html에서 찾는거니깐 id #으로 찾음! 
        // 포스트 글 번호 찾음
        const postId = document.querySelector('#id').value;
        // 댓글 작성자 아이디 찾음
        const writer = document.querySelector('#writer').value;
        // 댓글 내용 찾음.
        const replyText = document.querySelector('#replyText').value;
        
        // 댓글 작성자와 내용은 null, 비어있음 안됨.
        if(writer == '' || replyText =='') {
            alert('댓글 내용은 반드시!')
            return;
        }
        
        // Ajax POST 요청을 보낼 때 서버로 보내는 데이터 작성.
        const data = { // key:value  오브젝트를 만들때 사용함.
            postId: postId,  // 댓글이 달릴 포스트 아이디(번호)
            replyText: replyText, // 댓글 내용
            writer: writer  // 댓글 작성자
            
        }; // JavaScript Object
        
        
        // Axios 라이브러리를 사용해서 Ajax post 요청을 보냄.
        axios.post('/api/reply', data) // form에서 얻은 데이터를 보내줌.
            .then(response => {  // 성공 응답(response)이 도착했을 때 실행할 콜백
                console.log(response) 
                alert('#'+response.data + ' 댓글 등록 성공');
                clearInputs(); // 댓글 작성자와 내용에 작성된 문자열을 삭제
                readAllReplies(); // 댓글 목록을 다시 요청, 갱신.
                })      // 성공 응답이 왔을 때 실행할 callback 함수
            .catch(error => {   // 실패 응답(error)이 도착했을 때 실행할 콜백
                console.log(error); 
                });
        
                
    }
    
    
    
    function clearInputs() {
        document.querySelector('#writer').value = '';
        document.querySelector('#replyText').value = '';
        
    }
    
    function readAllReplies(){
        const postId = document.querySelector('#id').value; // 댓글이 달려있는 글번호
        
        axios
        .get('/api/reply/all/'+postId)   
        .then(response => { 
            console.log(response)  // 응답온 데이터
            updateReplyList(response.data) })  // 콘솔에서 response는 data를 가져오면 됨. controller에서 보내준 data, ok(list)를 보내줌.
        .catch(error => { console.log(error) });
    }
    
    function updateReplyList(data) {
        // 댓글들의 배열(data)을 HTML 영역에 보일 수 있도록 html 코드 작성.
        const divReplies = document.querySelector('#replies');
        
        let str = ''; // div 안에 들어갈 HTML 코드
        
        for(let r of data) {
            str +='<div class="card my-2">'
                    +'<div class = "card-header">'
                    + '<h5>' + r.writer + '</h5>'
                    +'</div>'
                    +'<div class = "card-body">'
                    +'<p>' + r.replyText + '</p>'
                    +'<p> 작성 시간 : ' + r.createdTime +'</p>'
                    +'<p> 수정 시간 : ' + r.modifiedTime +'</p>'
                    +'</div>'
                    
                    +'<div class="card-footer">'
                    + `<button type="button" class="btnModifies btn btn-outline-primary" data-rid="${r.replyId}" >수정</button>`
                    +'</div>'
                    
                + '</div>';
        }
        
        divReplies.innerHTML=str;
        
        // [수정] 버튼들이 HTML요소로 만들어진 이후에, [수정] 버튼에 이벤트 리스너를 등록.
        const buttons = document.querySelectorAll('.btnModifies'); // 버튼 배열이 됨>
        buttons.forEach(btn => {  // 반복문..!
            btn.addEventListener('click', getReply );
        });
        
    }
    
    function getReply(event){
    //        console.log(event.target); // 이벤트가 발생한 타겟 -> 버튼
        // 클릭된 버튼의 data-rid 속성값을 읽음.
        const rid= event.target.getAttribute('data-rid');
        console.log(rid);
        
        // 해당 댓글 아이디의 댓글 객체를 Ajax GET 방식으로 요청.
        axios
        .get('/api/reply/'+rid)
        .then(response => { showModal(response.data) })
        .catch(err => {console.log(err)});
        
    }    

    const divModal = document.querySelector('#replyModal');
    const replyModal = new bootstrap.Modal(divModal); // 부트스트랩 Modal 객체 생성.
    
    const modalReplyId = document.querySelector('#modalReplyId'); // 댓글 아이디
    const modalReplyText = document.querySelector('#modalReplyText');
    
    const modalBtnDelete = document.querySelector('#modalBtnDelete'); // 댓글 삭제 버튼
    const modalBtnUpdate = document.querySelector('#modalBtnUpdate'); // 댓글 수정 버튼
    
    
    
    function showModal(reply){
        // Modal 댓글 아이디/내용 채우기
        modalReplyId.value = reply.replyId;
        modalReplyText.value = reply.replyText;
        
        replyModal.show();  // 모달을 화면에 보여주기 
        
    }
    
    modalBtnDelete.addEventListener('click', deleteReply);
    modalBtnUpdate.addEventListener('click', updateReply);
    
    function deleteReply(event){
        const replyId = modalReplyId.value;  // 삭제할 댓글 id
        const result = confirm('삭제하시겠습니까:()');
        if(result){
            axios
            .delete('/api/reply/' + replyId) // Ajax DELETE 요청 전송
            .then(response => {
                alert(`#${response.data} 댓글 삭제 성공`);
                readAllReplies(); // 댓글 목록 갱신
            })  // 성공 응답 (HTTP 200 OK)
            .catch(err => {console.log(err)})  // 실패 응답 (HTTP 404, 405)
            .then(function () { // 내부(익명함수) try-catch-finally 구조와 비슷.필요시만 작성
                // 성공 응답 처리 또는 실패응답처리가 끝났을 때 무조건 실행할 문장.
                replyModal.hide(); // modal 닫기
            } ); 
            
            
            
        }
        
    }
    
    function updateReply(event){
        const replyId = modalReplyId.value; // 수정할 댓글 id
        const replyText = modalReplyText.value; //
        
        if(replyText==''){
            alert('댓글 내용 입려');
            return;
        }
        
        const result = confirm('수정완료???');
        if(result){
            const data = {replyText: replyText}; // key:value, Ajax 요청으로 보낼 데이터 전송
            
            axios
            .put('/api/reply/'+ replyId, data)  // Ajax PUT 요청을 전송
            .then(response => {
                alert(`#${response.data} 댓글 수정 성공`);   // '#' + response.data +'댓굴 슈정'
                readAllReplies(); //댓글 목록 갱신 
              })  // 성공응답처리
            .catch(err => {console.log(err)})   // 실패응답처리
            .then(function () {
                replyModal.hide();
            });  
        }
          
    }


    
    
});
 
 
 
 
 
 