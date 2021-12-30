<template>
  <div>
    <!-- 글 상세 보기 내용 -->
    <el-dialog title="글 상세 보기" :visible.sync="openDetail" width="50%" center :close-on-click-modal="false">
    글번호 : <el-input :value="boardDetail.b_no" style = "margin-bottom:10px; width:20%" >글번호</el-input>
                                        <br>
        글 번호 : {{ boardDetail.b_no }} <br>
        제목 : {{ boardDetail.subject }} <br>
        등록일 : {{ boardDetail.reg_date }} <br>
        내용 : {{ boardDetail.content }} <br>
        작성자 : {{ boardDetail.writer }} <br>

    <el-input style="margin-top:10px" placeholder="암호를 입력하세요" v-model="check_pwd" show-password v-show="vshow"></el-input>

        <el-row type="flex" justify="end" style="margin-top:30px;">
            <el-button type="primary" @click="clickEditButton()">수정</el-button>
            <el-button type="danger" @click="clickDeleteButton()">삭제</el-button>
        </el-row>
    </el-dialog>

    <BoardReg @reload="reload" ref="regPopup"></BoardReg>

  </div>
</template>


<script>

import axios from 'axios';  
import BoardReg from './BoardReg.vue';



export default {
    name: 'BoardDetail',
    components: { BoardReg },
    data() {
        return {
            openDetail: false,
            boardDetail: [],
            vshow: false,
            check_pwd: '',
        };
    },
    beforeUpdate(){
        if(this.openDetail == false || this.vshow==false){
            this.reset_pwd();
        }
    },
    computed: {
        setParams() {
            const params = {
                subject: this.subject,
                content: this.content,
            };
            return params;
        },
    },
    methods:{
        getBoardDetail(row) {
            const params = {
                b_no: row.b_no,
            };
            axios
                .post('http://localhost:8088/board/get-board-detail.do', params)
                .then((response) => {
                    if(response.data.success) {
                        this.boardDetail = response.data.result;
                        this.openDetail = true;
                    }
                })
                .catch(function(error) {
                    console.log(error);
                });
        },
        clickEditButton() {

            this.openDetail = false;
            this.$refs.regPopup.b_no = this.boardDetail.b_no;
            this.$refs.regPopup.subject = this.boardDetail.subject;
            this.$refs.regPopup.content = this.boardDetail.content;
            this.$refs.regPopup.writer = this.boardDetail.writer;
            this.$refs.regPopup.email = this.boardDetail.email;

            this.$refs.regPopup.check_pwd = this.boardDetail.pwd;

            this.$refs.regPopup.editMode = true;
            this.$refs.regPopup.openPopup = true;

        
        },
        clickDeleteButton() {
            const params = {
                b_no: this.boardDetail.b_no,
            };

            if(this.vshow == false) {

                this.vshow = true;
                alert("삭제하려면 암호를 입력해주세요");
                return;
            }
            else{
                if(confirm("정말 삭제하시겠습니까?") && this.vshow==true){

                    if(this.check_pwd === this.boardDetail.pwd){
                        axios
                            .post('http://localhost:8088/board/delete-board.do', params)
                            .then((response) => {
                                if(response.data.success) {
                                    this.openDetail = false;

                                    // this.reset_pwd();
                                    // this.check_pwd = '';
                                    // this.vshow = false;

                                    console.log('삭제완료 b_no' + params.b_no)
                                    alert("삭제 완료.")
                                    this.reload();
                                }               
                            })
                            .catch(function(error){
                                console.log(error); 
                            });
                    }else{
                        alert("암호가 틀렸습니다.\n다시 확인바랍니다.");
                        this.check_pwd = '';
                    
                        return;
                    }

                   
                }
                else {
                    return;
                }
            }
        },
        reload() {
            // 목록을 재 로딩을 위해 이벤트 emit    
            this.$emit('reload');
        },
        reset_pwd() {
            this.check_pwd = '';
            this.vshow = false;
        }
    }

}
</script>

<style scoped>

</style>

