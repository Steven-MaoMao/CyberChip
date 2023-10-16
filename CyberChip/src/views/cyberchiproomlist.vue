<template>
    <v-container class="header rounded-xl">
        <div class="d-flex align-center">
            <router-link to="/" class="d-flex align-center" style="text-decoration: none; color: black;">
                <div class="text-h4 text-no-wrap" style="margin: 10px;">
                    赛博筹码
                </div>
            </router-link>
        </div>
    </v-container>
    <v-container id="block" class="rounded-xl">
        <v-btn id="create_room_dialog_visible_button" prepend-icon="mdi-plus-circle-outline" size="large" variant="outlined"
            @click="createRoomDialogVisible = true">
            创建房间
        </v-btn>
        <v-divider class="border-opacity-50" style="margin-top: 20px; margin-bottom: 20px;"></v-divider>
        <div v-if="Object.keys(cyberChipRoomList).length <= 0" class="d-flex justify-center align-center">
            <v-btn id="create_room_dialog_visible_button" prepend-icon="mdi-plus-circle-outline" size="large"
                @click="createRoomDialogVisible = true">
                创建房间
            </v-btn>
        </div>
        <div v-else class="d-flex flex-wrap">
            <v-card v-for="room in cyberChipRoomList" :title="room.roomName" :subtitle="'房主：' + room.roomManager"
                class="room_card" @click="onJoinRoomDialogVisible(room.roomName)"></v-card>
        </div>
    </v-container>
    <v-dialog v-model="createRoomDialogVisible" width="50%">
        <v-card title="创建房间">
            <div style="margin: 24px;">
                <v-form v-model="createRoomForm" @submit.prevent="createRoom">
                    <v-text-field v-model="newRoom.roomName" :rules="[required]" label="房间名称" variant="outlined"
                        style="margin-bottom: 12px;"></v-text-field>
                    <v-text-field v-model="newRoom.roomManager" :rules="[required]" label="自己昵称" variant="outlined"
                        style="margin-bottom: 12px;"></v-text-field>
                    <v-text-field v-model="newRoom.roomPassword" :rules="[required]" label="房间密码" variant="outlined"
                        style="margin-bottom: 12px;"></v-text-field>
                    <v-btn size="large" block variant="outlined" type="submit" color="success"
                        style="margin-top: 20px; margin-bottom: 20px;">
                        确认
                    </v-btn>
                    <v-btn @click="cancelCreateRoom" size="large" block variant="outlined" color="error">
                        取消
                    </v-btn>
                </v-form>
            </div>
        </v-card>
    </v-dialog>
    <v-dialog v-model="joinRoomDialogVisible" width="50%">
        <v-card title="加入房间">
            <div style="margin: 24px;">
                <v-form v-model="joinRoomForm" @submit.prevent="joinRoom">
                    <v-text-field v-model="joinRoomData.name" :rules="[required]" label="自己昵称" variant="outlined"
                        style="margin-bottom: 12px;"></v-text-field>
                    <v-text-field v-model="joinRoomData.roomPassword" :rules="[required]" label="房间密码" variant="outlined"
                        type="password" style="margin-bottom: 12px;"></v-text-field>
                    <v-btn size="large" block variant="outlined" type="submit" color="success"
                        style="margin-top: 20px; margin-bottom: 20px;">
                        确认
                    </v-btn>
                    <v-btn @click="cancelJoinRoom" size="large" block variant="outlined" color="error">
                        取消
                    </v-btn>
                </v-form>
            </div>
        </v-card>
    </v-dialog>
    <v-snackbar v-model="snackbar.show" timeout="2000" :color="snackbar.color">
        {{ snackbar.text }}
    </v-snackbar>
</template>

<script>
export default {
    data() {
        return {
            interval: 0,
            cyberChipRoomList: {},
            createRoomDialogVisible: false,
            newRoom: {
                roomName: null,
                roomManager: null,
                roomPassword: null,
            },
            createRoomForm: false,
            joinRoomDialogVisible: false,
            joinRoomForm: false,
            joinRoomData: {
                roomName: null,
                name: null,
                roomPassword: null,
            },
            snackbar: {
                show: false,
                text: null,
                color: null,
            },
        }
    },
    watch: {
        createRoomDialogVisible(newCreateRoomDialogVisible) {
            if (newCreateRoomDialogVisible === false) {
                this.newRoom = {
                    roomName: null,
                    roomManager: null,
                    roomPassword: null,
                }
            }
        },
        joinRoomDialogVisible(newJoinRoomDialogVisible) {
            if (newJoinRoomDialogVisible === false) {
                this.joinRoomData = {
                    roomName: null,
                    name: null,
                    roomPassword: null,
                }
            }
        }
    },
    mounted() {
        document.title = '赛博筹码房间大厅'
        this.getCyberChipRoomList()
        let _this = this
        this.interval = setInterval(function () {
            _this.getCyberChipRoomList()
        }, 1000)
    },
    unmounted() {
        clearInterval(this.interval)
    },
    methods: {
        getCyberChipRoomList() {
            this.axios
                .get(this.baseURL + '/cyberchip/getCyberChipRoomList')
                .then((result) => {
                    if (result.data.flag === false) {
                        this.snackbar.color = 'error'
                        this.snackbar.text = '获取数据失败，寄了'
                        this.snackbar.show = true
                    } else {
                        this.cyberChipRoomList = result.data.data
                    }
                })
                .catch((err) => {
                    this.snackbar.color = 'error'
                    this.snackbar.text = '获取数据失败，寄了'
                    this.snackbar.show = true
                    console.log(err)
                })
        },
        createRoom() {
            if (!this.createRoomForm) {
                return
            }
            sessionStorage.setItem('name', this.newRoom.roomManager)
            this.axios
                .post(this.baseURL + '/cyberchip/createRoom', this.newRoom)
                .then((result) => {
                    if (result.data.flag === true) {
                        this.snackbar.color = 'success'
                        sessionStorage.setItem('token', result.data.data)
                        this.$router.push('/cyberchip')
                    } else {
                        this.snackbar.color = 'error'
                    }
                    this.snackbar.text = result.data.message
                    this.snackbar.show = true
                })
                .catch((err) => {
                    this.snackbar.color = 'error'
                    this.snackbar.text = '创建失败，寄了'
                    this.snackbar.show = true
                    console.log(err)
                })
            this.cancelCreateRoom()
        },
        cancelCreateRoom() {
            this.createRoomDialogVisible = false
        },
        onJoinRoomDialogVisible(roomName) {
            this.joinRoomData.roomName = roomName
            this.joinRoomDialogVisible = true
        },
        joinRoom() {
            if (!this.joinRoomForm) {
                return
            }
            sessionStorage.setItem('name', this.joinRoomData.name)
            this.axios
                .post(this.baseURL + '/cyberchip/joinRoom', this.joinRoomData)
                .then((result) => {
                    if (result.data.flag === true) {
                        this.snackbar.color = 'success'
                        sessionStorage.setItem('token', result.data.data)
                        this.$router.push('/cyberchip')
                    } else {
                        this.snackbar.color = 'error'
                    }
                    this.snackbar.text = result.data.message
                    this.snackbar.show = true
                })
                .catch((err) => {
                    this.snackbar.color = 'error'
                    this.snackbar.text = '加入失败，寄了'
                    this.snackbar.show = true
                    console.log(err)
                })
            this.cancelJoinRoom()
        },
        cancelJoinRoom() {
            this.joinRoomDialogVisible = false
        },
        required(v) {
            return !!v || '填一下'
        },
    }
}
</script>

<style scoped>
.header {
    width: 60%;
    background: rgba(255, 255, 255, 0.8);
    padding-left: 30px;
    padding-right: 30px;
}

#block {
    width: 60%;
    background: rgba(255, 255, 255, 0.8);
    margin-top: 20px;
    margin-bottom: 20px;
    padding: 30px;
}

#create_room_dialog_visible_button {
    margin-left: 1%;
    margin-right: 1%;
    margin-top: 10px;
    margin-bottom: 10px;
}

.room_card {
    width: 23%;
    margin-left: 1%;
    margin-right: 1%;
    margin-top: 10px;
    margin-bottom: 10px;
}
</style>