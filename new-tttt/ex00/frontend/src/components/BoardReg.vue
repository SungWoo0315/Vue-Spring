<template>
    <div>
        <!-- 글쓰기 버튼 -->
        <!-- 글쓰기 버튼 밖으로 빼내서  RegistBoard.vue는 등록 팝업만으로만 사용  -->
        <!-- <el-button type="danger" @click="openPopup = true">글쓰기</el-button> -->
        <!-- 팝업 내용 -->
        <el-dialog :title="ModalTitle" :visible.sync="openPopup" width="30%" center :close-on-click-modal="false">
            <!-- 본문 영역 -->
            <!-- <span>글쓰기 영역입니다.</span> -->
            <el-input placeholder="제목을 입력해 주세요" v-model="subject"></el-input>
            <el-input style="margin-top:10px" placeholder="작성자를 입력해 주세요" v-model="writer"></el-input>
            <el-input style="margin-top:10px" placeholder="이메일을 입력해 주세요" v-model="email"></el-input>
            <el-input
                style="margin-top:20px"
                type="textarea"
                :rows="10"
                placeholder="내용을 입력해 주세요"
                v-model="content"
            >
            </el-input>
            <el-input style="margin-top:10px" placeholder="암호를 입력하세요" v-model="pwd" show-password></el-input>




            <!-- 이미지 업로드 블로그 참조 -->
            <!-- https://junhyunny.github.io/spring-boot/vue.js/multipartfile/ -->
             <h3>파일 업로드 결과: { { this.response === '' ? 'waiting' : this.response } }</h3>
            <div>
                <button @click="selectUploadFile()">이미지 선택</button>
            </div>



            <!-- 이미지 업로드 form -->
            <!-- <form method="post" action="http://localhost:8088/board/reg-board.do" enctype="multipart/form-data">
                <label>파일:</label>
                <input multiple="multiple" type="file" name="fileList">
                <input type="submit" value="upload">
            </form> -->


            <!-- 이미지 업로드 el-element -->
            <!-- 자동 업로드 인듯.? -->
            <!-- 이미지 업로드 진행중.... 아직 미완성 -->
            <!-- <el-upload
                class="fileList"
                action="http://localhost:8088/board/file-upload.do"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :before-remove="beforeRemove"
                multiple
                :limit="1"
                :on-exceed="handleExceed"
                :file-list="fileList">
                <el-button size="small" type="primary">Click to upload</el-button>
                <div slot="tip" class="el-upload__tip">jpg/png files with a size less than 50mb</div>
            </el-upload> -->

            <!-- 수동업로드... -->
            <!-- <el-upload
                class="fileList"
                ref="upload"
                action="http://localhost:8088/board/reg-board.do"
                :auto-upload="false">
                <el-button slot="trigger" size="small" type="primary">select file</el-button>
                <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">upload to server</el-button>
                <div class="el-upload__tip" slot="tip">jpg/png files with a size less than 50mb</div>
            </el-upload> -->






            <!-- footer 영역 -->
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="reg_board()">확인</el-button>   
                <el-button @click="openPopup = false">취소</el-button>
            </span>
        </el-dialog>
    
    </div>
</template>

<script>
import axios from 'axios';


export default {

    name: 'BoardReg',
    data() {
        return {
            b_no: '',
            editMode: false,

            openPopup: false,
            subject: '',
            writer: '',
            content: '',
            email: '',
            pwd: '',

            // 글 수정 시 암호 체크 확인
            check_pwd:'',

            // 이미지 업로드
            // fileList: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}],
            // fileList:[],

        };
    },
    beforeUpdate(){
        if(this.openPopup == false){
            this.reset_pwd();
        }
    },
    computed: {
        // 서버단에 넘길 값들
        setParams() {
            const params = {
                b_no: this.b_no,
                subject: this.subject,
                writer: this.writer,
                content: this.content,
                email: this.email,
                pwd: this.pwd,
            };
            return params;
        },
        ModalTitle() {
            return this.editMode === true ? '글 수정' : '글 등록';
        }
    },
    methods: {
            selectUploadFile(){
                 // var vue = this
                let elem = document.createElement('input')
                // 이미지 파일 업로드 / 동시에 여러 파일 업로드
                elem.id = 'image'
                elem.type = 'file'
                elem.accept = 'image/*'
                elem.multiple = true
                // 클릭
                elem.click();
                // 이벤트 감지
                elem.onchange = function(){
                    const formData = new FormData()
                    for (var index = 0; index < this.files.length; index++) {
                    formData.append('fileList', this.files[index])
                    }
                }

            },
            reg_board() {
            // 글 수정이 아니라면 글 등록 호출
            if(!this.editMode){

                    axios
                        .post('http://localhost:8088/board/reg-board.do', this.setParams, this.formData, { headers: { 'Content-Type': 'multipart/form-data' } })
                        .then((response) => {
                            if(response.data.success || response.data.result) {
                                // 창의 화면 변수 fasle 로 창 닫기
                                this.openPopup = false;
                                // 입력되어 있는 변수들의 값을 초기화 
                                this.subject = '';
                                this.writer = '';
                                this.content ='';
                                this.email = '';
                                this.pwd = '';
                                // 목록 재로딩을 위한 이벤트 emit  
                                this.$emit('reload')

                                // 콘솔로그 및 alert 
                                console.log("글 등록 axios post 성공")
                                console.log(response);
                                alert("글 등록 성공!")
                            }
                        })
                        .catch(function(error) {
                            console.log("글 등록 axios post 에러")
                            alert("등록 실패... 오류 확인하세요")
                            console.log(error);
                        });
            }
            
            // 글 수정을 호출
            else {


                if(this.check_pwd==this.pwd){
                    
                

                    axios
                        .post('http://localhost:8088/board/edit-board.do', this.setParams)
                        .then((response) => {
                                if(response.data.success || response.data.result) {
                                // 창의 화면 변수 fasle 로 창 닫기
                                this.openPopup = false;
                                // 입력되어 있는 변수들의 값을 초기화 
                                this.subject = '';
                                this.writer = '';
                                this.content ='';
                                this.email = '';
                                this.pwd = '';
                                // 목록 재로딩을 위한 이벤트 emit  
                                this.$emit('reload')

                                // 콘솔로그 및 alert 
                                console.log("글 수정 axios post 성공")
                                console.log(response);
                                alert("글 수정 성공!")
                            }
                        })
                        .catch(function(error) {
                            console.log("글 수정 axios post 에러")
                            alert("수정 실패... 오류 확인하세요")
                            console.log(error);
                        })
                
                
                
                }
                else{
                    alert("암호가 틀렸습니다 \n암호를 확인해주세요")
                    return;
                }
            }
        },
        // 임시 세이브용 reg_board()
        /*
        reg_board() {
            // 글 수정이 아니라면 글 등록 호출
            if(!this.editMode){
                axios
                    .post('http://localhost:8088/board/reg-board.do', this.setParams)
                    .then((response) => {
                        if(response.data.success || response.data.result) {
                            // 창의 화면 변수 fasle 로 창 닫기
                            this.openPopup = false;
                            // 입력되어 있는 변수들의 값을 초기화 
                            this.subject = '';
                            this.writer = '';
                            this.content ='';
                            this.email = '';
                            this.pwd = '';
                            // 목록 재로딩을 위한 이벤트 emit  
                            this.$emit('reload')

                            // 콘솔로그 및 alert 
                            console.log("글 등록 axios post 성공")
                            console.log(response);
                            alert("글 등록 성공!")
                        }
                    })
                    .catch(function(error) {
                        console.log("글 등록 axios post 에러")
                        alert("등록 실패... 오류 확인하세요")
                        console.log(error);
                    });
            }
            // 글 수정을 호출
            else {


                if(this.check_pwd==this.pwd){
                    
                

                    axios
                        .post('http://localhost:8088/board/edit-board.do', this.setParams)
                        .then((response) => {
                                if(response.data.success || response.data.result) {
                                // 창의 화면 변수 fasle 로 창 닫기
                                this.openPopup = false;
                                // 입력되어 있는 변수들의 값을 초기화 
                                this.subject = '';
                                this.writer = '';
                                this.content ='';
                                this.email = '';
                                this.pwd = '';
                                // 목록 재로딩을 위한 이벤트 emit  
                                this.$emit('reload')

                                // 콘솔로그 및 alert 
                                console.log("글 수정 axios post 성공")
                                console.log(response);
                                alert("글 수정 성공!")
                            }
                        })
                        .catch(function(error) {
                            console.log("글 수정 axios post 에러")
                            alert("수정 실패... 오류 확인하세요")
                            console.log(error);
                        })
                
                
                
                }
                else{
                    alert("암호가 틀렸습니다 \n암호를 확인해주세요")
                    return;
                }
            }
        },
        */
        reset_pwd() {
            this.pwd = '';
            this.openPopup = false;
        },
        // 이미지 업로드
        // handleRemove(file, fileList) {
        //     console.log(file, fileList);
        // },
        // handlePreview(file) {
        //     console.log(file);
        //     console.log(typeof(file));
        //     console.log(file.name);
        
        // },
        // handleExceed(files, fileList) {
        //     this.$message.warning(`The limit is 3, you selected ${files.length} files this time, add up to ${files.length + fileList.length} totally`);
        // },
        // beforeRemove(file) {
        //     return this.$confirm(`Cancel the transfert of ${ file.name } ?`);
        // },
        // submitUpload() {
        //     this.$refs.upload.submit();
        // }



    },

};
</script>

<style scoped>

</style>