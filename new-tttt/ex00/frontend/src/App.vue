<template>
<div>
  <div id="app">
    <img alt="Vue logo" src="./assets/logo.png">
    <HelloWorld msg="Test Board Vue.js App"/>
  </div>
  <div id="app2">
    <el-button type="danger" @click="openRegPopup()">글쓰기</el-button>
  </div>
    <BoardReg @reload="reload" ref="regPopup"></BoardReg>
    <BoardList ref="list"></BoardList>
</div>
</template>

<script>
import HelloWorld from './components/HelloWorld.vue'
import BoardList from './components/BoardList.vue'
import BoardReg from './components/BoardReg.vue'

export default {
  name: 'App',
  components: {
    HelloWorld,
    BoardList,
    BoardReg
  },
  methods: {
    reload(){
      this.$refs.list.getBoardList();
    },
    openRegPopup() {
      this.$refs.regPopup.openPopup = true;
    }
  },
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
#app2 {
  text-align: right;
}

</style>
