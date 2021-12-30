<template>
    <div class="boardlist">

        <!-- Element-ui를 사용 -->
        <div>
            <el-table border 
                :data="boardList" 
                stripe
                @row-click="clickRow"
                @slot="empty"
                :default-sort = "{prop: 'b_no', order: 'descending'}"
            >
            <el-table-column prop="b_no" label="글번호"></el-table-column>
            <el-table-column prop="pic" label="사진"></el-table-column>
            <el-table-column prop="subject" label="제목"></el-table-column>
            <el-table-column prop="content" label="내용"></el-table-column>
            <el-table-column prop="writer" label="작성자"></el-table-column>
            <el-table-column prop="reg_date" label="등록일" sortable></el-table-column>
            </el-table>

            <!-- 상세보기 컴포넌트화 작업 -->
            <board-detail ref="detailPopup" @reload="getBoardList()"></board-detail>
            

            <el-pagination
                layout="prev, pager, next"
                :page-size="20"
                :pager-count="11"
                :total="2000">
            </el-pagination>

            <!-- 글 상세 보기 내용 -->
            <!-- 컴포넌트로 BoardDetail.vue에 따로 빼주었음 -->
            <!-- <el-dialog title="글 상세 보기" :visible.sync="openDetail" width="50%" center>

            글번호 : <el-input :value="boardDetail.b_no" style = "margin-bottom:10px; width:20%" >글번호</el-input>
                                                <br>
                글 번호 : {{ boardDetail.b_no }} <br>
                제목 : {{ boardDetail.subject }} <br>
                등록일 : {{ boardDetail.reg_date }} <br>
                내용 : {{ boardDetail.content }} <br>
                작성자 : {{ boardDetail.writer }} <br>
            </el-dialog> -->
        </div>


       

        <!-- 순수 HTML -->
        <!-- <table>
            <tr>
                <th>번호</th> <th>사진</th> <th>제목</th> <th>글쓴이</th> <th>작성일</th>
            </tr>
            <tr v-for="(tt,i) in boardList" :key="i">
                <td>
                    {{tt.b_no}}
                </td>
                <td>
                    {{tt.pic}}
                </td>
                <td>
                    {{tt.subject}}
                </td>
                <td>
                    {{tt.writer}}
                </td>
                <td>
                    {{tt.reg_date}}
                </td>
            </tr>
        </table> -->


        <!-- =============================================== -->
        <!-- 데이터 가져오는 테스트 -->
        <!-- {{ boardList }} -->

        <!-- v-for 테스트용 -->
        <!-- <div v-for="(tt,i) in boardList" :key="i">
            <p>{{tt.subject}}</p>
        </div> -->
    </div>
</template>

<script>
    import axios from 'axios';
    import BoardDetail from './BoardDetail.vue';
    
export default {
    name: 'BoardList',
    // 상세보기 컴포넌트화 작업
    components: { BoardDetail },
    data() {
        return {
        boardList: [],
        boardDetail:[],
        openDetail: false,
        empty: "empty",
        };
    },
    // props: {
    //     msg: String,
    // },
    created() {
        this.getBoardList();
    },
    methods: {
        getBoardList() {
        axios
            .get('http://localhost:8088/board/get-board-list.do')
            .then((response) => {
            if (response.data.success) {
                console.log("getBoardList() 에서 받은데이터 확인 =====");                
                console.log(response.data.result);
                this.boardList = response.data.result;
            }
            })
            .catch(function(error) {
                console.log("getBoardList() 에서 error =====");                
                console.log(error);
            });
        },

        // 상세보기 컴포넌트화를 위한 주석처리.
        // getBoardDetail(row) {
        //     const params = {
        //         b_no: row.b_no,
        //     };
        //     axios
        //         .post('http://localhost:8088/board/get-board-detail.do', params)
        //         .then((response) => {
        //             if(response.data.success) {
        //                 this.boardDetail = response.data.result;
        //                 this.openDetail = true;
        //             }
        //         })
        //         .catch(function(error) {
        //             console.log(error);
        //         })

        //     console.log(row.b_no);
        //     console.log(row)
        //     // console.log(column);
        //     // console.log(event);
        // }


        clickRow(row) {
            this.$refs.detailPopup.getBoardDetail(row);
            console.log("클릭한 b_no 확인 ==> "+row.b_no);
        }
    },
};
</script>

<style>
    table {
    width: 100%;
    border-top: 1px solid #444444;
    border-collapse: collapse;
    }
    th, td {
    border-bottom: 1px solid #444444;
    padding: 10px;
    }
    /* table {
    width: 100%;
    border: 1px solid #444444;
    border-collapse: collapse;
    }
    th, td {
        border: 1px solid #444444;
    } */
    h3 {
    margin: 40px 0 0;
    }
    ul {
    list-style-type: none;
    padding: 0;
    }
    li {
    display: inline-block;
    margin: 0 10px;
    }
    a {
    color: #42b983;
    }
</style>