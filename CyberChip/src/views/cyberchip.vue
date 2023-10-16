<template>
    <div class="d-flex" v-if="this.cyberChip.gaming === true" style="height: 100vh; padding: 20px;">
        <v-card class="rounded-xl" style="height: 100%; width: 250px; overflow: auto; margin-right: 40px;">
            <v-table density="compact">
                <thead>
                    <tr>
                        <th class="text-center text-overline font-weight-black" style="padding: 0px 4px;">
                            昵称
                        </th>
                        <th class="text-center text-overline font-weight-black" style="padding: 0px 4px;">
                            本轮下注
                        </th>
                        <th class="text-center text-overline font-weight-black" style="padding: 0px 4px;">
                            总共下注
                        </th>
                        <th class="text-center text-overline font-weight-black" style="padding: 0px 4px;">
                            资金
                        </th>
                    </tr>
                </thead>
                <tbody class="text-center text-overline">
                    <tr v-for="member in cyberChip.memberList"
                        :style="member.name === name ? { color: 'rgb(76, 175, 80)' } : {}">
                        <td style="padding: 0px 4px;">
                            {{ member.name }}
                        </td>
                        <td style="padding: 0px 4px;">
                            {{ member.thisRoundAnte }}
                        </td>
                        <td style="padding: 0px 4px;">
                            {{ member.allAnte }}
                        </td>
                        <td style="padding: 0px 4px;">
                            {{ member.bankRoll }}
                        </td>
                    </tr>
                </tbody>
            </v-table>
        </v-card>
        <div class="flex-grow-1 d-flex flex-column justify-space-between">
            <div class="d-flex justify-space-between" style="height: 40%;">
                <v-card class="rounded-xl" style="width: 48%; padding: 10px 16px;">
                    <div class="d-flex flex-column" style="height: 100%;">
                        <div class="text-h5">
                            {{ name }}
                        </div>
                        <div class="d-flex justify-space-between" style="height: 100%; margin-top: 12px;">
                            <div class="d-flex flex-column" style="width: 50%; height: 100%;">
                                <div>
                                    本轮下注：
                                </div>
                                <div class="text-h2 flex-grow-1 d-flex justify-center align-center">
                                    <div>
                                        {{ cyberChip.memberList[index].thisRoundAnte }}
                                    </div>
                                </div>
                            </div>
                            <div class="d-flex flex-column" style="width: 45%; height: 100%;">
                                <div>
                                    总共下注：{{ cyberChip.memberList[index].allAnte }}
                                </div>
                                <div>
                                    资金：{{ cyberChip.memberList[index].bankRoll }}
                                </div>
                            </div>
                        </div>
                    </div>
                </v-card>
                <v-card class="rounded-xl" style="width: 48%; padding: 10px 16px;">
                    <div class="d-flex flex-column" style="height: 100%;">
                        <div class="text-h5">
                            荷官
                        </div>
                        <div class="d-flex justify-space-between" style="height: 100%; margin-top: 12px;">
                            <div class="d-flex flex-column" style="width: 50%; height: 100%;">
                                <div>
                                    底池：
                                </div>
                                <div class="text-h2 flex-grow-1 d-flex justify-center align-center">
                                    <div>
                                        {{ cyberChip.pot }}
                                    </div>
                                </div>
                            </div>
                            <div class="d-flex flex-column" style="width: 45%; height: 100%;">
                                <div style="margin-bottom: 10px;">
                                    总资金：{{ cyberChip.chipCount }}
                                </div>
                                <div v-for="sidePot, i in this.cyberChip.sidePotList">
                                    边池 {{ i + 1 }}：{{ sidePot }}
                                </div>
                            </div>
                        </div>
                    </div>
                </v-card>
            </div>
            <v-card class="rounded-xl d-flex flex-column justify-space-between"
                style="width: 100%; height: 55%; padding: 16px;">
                <div class="flex-grow-1 d-flex justify-space-between">
                    <div class="d-flex flex-column justify-space-between" style="width: 30%;">
                        <div class="d-flex justify-space-between align-center" style="height: 30%;">
                            <v-chip class="d-flex justify-center align-center" variant="outlined" label
                                @click="changeNumber(-5)" style="width: 30%; height: 100%;">
                                <v-icon icon="mdi-minus" />
                            </v-chip>
                            <span class="text-h6">
                                5
                            </span>
                            <v-chip class="d-flex justify-center align-center" variant="outlined" label
                                @click="changeNumber(5)" style="width: 30%; height: 100%;">
                                <v-icon icon="mdi-plus" />
                            </v-chip>
                        </div>
                        <div class="d-flex justify-space-between align-center" style="height: 30%;">
                            <v-chip class="d-flex justify-center align-center" variant="outlined" label
                                @click="changeNumber(-20)" style="width: 30%; height: 100%;">
                                <v-icon icon="mdi-minus" />
                            </v-chip>
                            <span class="text-h6">
                                20
                            </span>
                            <v-chip class="d-flex justify-center align-center" variant="outlined" label
                                @click="changeNumber(20)" style="width: 30%; height: 100%;">
                                <v-icon icon="mdi-plus" />
                            </v-chip>
                        </div>
                        <div class="d-flex justify-space-between align-center" style="height: 30%;">
                            <v-chip class="d-flex justify-center align-center" variant="outlined" label
                                @click="changeNumber(-100)" style="width: 30%; height: 100%;">
                                <v-icon icon="mdi-minus" />
                            </v-chip>
                            <span class="text-h6">
                                100
                            </span>
                            <v-chip class="d-flex justify-center align-center" variant="outlined" label
                                @click="changeNumber(100)" style="width: 30%; height: 100%;">
                                <v-icon icon="mdi-plus" />
                            </v-chip>
                        </div>
                    </div>
                    <div class="d-flex flex-column justify-space-between" style="width: 30%;">
                        <v-btn variant="outlined" @click="ante" style="height: 30%;">
                            下注 {{ tempAnte }}
                        </v-btn>
                        <v-btn variant="outlined" @click="changeBankRoll" style="height: 30%;">
                            印钞 {{ tempBankRoll }}
                        </v-btn>
                        <v-btn variant="outlined" @click="addSidePot" style="height: 30%;">
                            边池 {{ tempSidePot }}
                        </v-btn>
                    </div>
                    <div class="d-flex flex-column justify-space-between" style="width: 30%;">
                        <v-btn variant="outlined" @click="getRice" style="height: 30%;" color="success"
                            :disabled="cyberChip.pot <= 0">
                            收米(底) {{ cyberChip.pot }}
                        </v-btn>
                        <v-btn variant="outlined" style="height: 30%;" :disabled="cyberChip.sidePotList.length <= 1">
                            选择边池
                            <v-menu activator="parent">
                                <v-list>
                                    <v-list-item v-for="sidePot, i in cyberChip.sidePotList" @click="chosenSidePot = i">
                                        <v-list-item-title>
                                            边池 {{ i + 1 }}：{{ sidePot }}
                                        </v-list-item-title>
                                    </v-list-item>
                                </v-list>
                            </v-menu>
                        </v-btn>
                        <v-btn variant="outlined" @click="getSideRice" style="height: 30%;" color="success"
                            :disabled="cyberChip.sidePotList.length === 0">
                            收米(边{{ chosenSidePot + 1 }}) {{ cyberChip.sidePotList[chosenSidePot] }}
                        </v-btn>
                    </div>
                </div>
                <v-divider class="border-opacity-25" style="margin-top: 2%; margin-bottom: 2%;"></v-divider>
                <div class="d-flex justify-space-between">
                    <v-btn variant="outlined" @click="toZero" style="width: 30%;">
                        归零
                    </v-btn>
                    <v-btn prepend-icon="mdi-stop" variant="outlined" color="error" @click="stopDialogVisible = true"
                        style="width: 30%;" :disabled="this.name !== this.cyberChip.roomManager">
                        结束游戏
                    </v-btn>
                </div>
            </v-card>
        </div>
        <v-dialog v-model="stopDialogVisible" width="50%">
            <v-card title="确认结束游戏吗？">
                <div style="margin: 24px;">
                    <v-btn @click="stop" size="large" block variant="outlined" color="success" style="margin-bottom: 16px;">
                        确认
                    </v-btn>
                    <v-btn @click="stopDialogVisible = false" size="large" block variant="outlined" color="error">
                        取消
                    </v-btn>
                </div>
            </v-card>
        </v-dialog>
    </div>
    <div v-else>
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
            <div class="d-flex align-center">
                <div class="text-h5 me-auto" style="margin-left: 2%;">
                    {{ this.cyberChip.roomName }}
                </div>
                <v-btn style="margin-right: 2%;" prepend-icon="mdi-play" variant="outlined" color="success"
                    :disabled="this.name !== this.cyberChip.roomManager || this.cyberChip.memberList.length <= 1"
                    @click="playDialogVisible = true">
                    开始游戏
                </v-btn>
                <v-btn style="margin-right: 2%;" prepend-icon="mdi-swap-horizontal" variant="outlined"
                    :disabled="this.name !== this.cyberChip.roomManager || this.cyberChip.memberList.length <= 1"
                    @click="exchangeMemberDialogVisible = true">
                    交换位置
                </v-btn>
            </div>
            <v-divider class="border-opacity-50" style="margin-top: 20px; margin-bottom: 20px;"></v-divider>
            <div class="d-flex flex-wrap">
                <v-card v-for="member in cyberChip.memberList" class="member_card"
                    :style="member.name === name ? { border: '1px solid rgb(76, 175, 80)', color: 'rgb(76, 175, 80)' } : {}">
                    <div class="d-flex align-center">
                        <div style="margin-right: 10px;">
                            <v-icon :icon="member.name === cyberChip.roomManager ? 'mdi-account-star' : 'mdi-account'"
                                size="30" />
                        </div>
                        <div class="text-subtitle-1">
                            {{ member.name }}
                        </div>
                    </div>
                </v-card>
            </div>
        </v-container>
        <v-dialog v-model="playDialogVisible" width="50%">
            <v-card title="开始游戏">
                <v-form v-model="playForm" @submit.prevent="play" style="margin: 24px;">
                    <v-text-field v-model="playData.chipCount" :rules="[required, min]" label="筹码总数" variant="outlined"
                        type="number"></v-text-field>
                    <v-btn type="submit" size="large" block variant="outlined" color="success"
                        style="margin-top: 20px; margin-bottom: 20px;">
                        确认
                    </v-btn>
                    <v-btn @click="cancelPlay" size="large" block variant="outlined" color="error">
                        取消
                    </v-btn>
                </v-form>
            </v-card>
        </v-dialog>
        <v-dialog v-model="exchangeMemberDialogVisible" width="50%">
            <v-card title="交换成员顺序">
                <div style="margin: 24px;">
                    <div class="d-flex align-content-start flex-wrap">
                        <v-checkbox v-for="member, index in cyberChip.memberList" v-model="exchangeList" :value=index
                            density="compact" @update:modelValue="update" style="width: 50%;">
                            <template v-slot:label>
                                <div class="text-h6" style="margin-left: 10px;">
                                    {{ member.name }}
                                </div>
                            </template>
                        </v-checkbox>
                    </div>
                    <div v-show="exchangeMemberErrorMessageVisible" class="text-caption"
                        style="color: red; margin-bottom: 20px;">
                        没有两个人怎么交换？？？
                    </div>
                    <v-btn @click="exchangeMember" size="large" block variant="outlined" color="success"
                        style="margin-bottom: 20px;">
                        确认
                    </v-btn>
                    <v-btn @click="cancelExchangeMember" size="large" block variant="outlined" color="error">
                        取消
                    </v-btn>
                </div>
            </v-card>
        </v-dialog>
    </div>
    <v-snackbar v-model="snackbar.show" timeout="2000" :color="snackbar.color">
        {{ snackbar.text }}
    </v-snackbar>
</template>

<script>
export default {
    data() {
        return {
            name: null,
            index: -1,
            interval: 0,
            cyberChip: {},
            playDialogVisible: false,
            playForm: false,
            playData: {
                chipCount: null,
            },
            exchangeMemberDialogVisible: false,
            exchangeMemberErrorMessageVisible: false,
            snackbar: {
                show: false,
                text: null,
                color: null,
            },
            exchangeList: [],
            tempAnte: 0,
            tempBankRoll: 0,
            tempSidePot: 0,
            chosenSidePot: 0,
            beforeUnload_time: 0,
            gap_time: 0,
            stopDialogVisible: false,
        }
    },
    watch: {
        playDialogVisible(newPlayDialogVisible) {
            if (newPlayDialogVisible === false) {
                this.playData = {
                    chipCount: null
                }
            }
        },
        exchangeMemberDialogVisible(newExchangeMemberDialogVisible) {
            if (newExchangeMemberDialogVisible === false) {
                this.exchangeMemberErrorMessageVisible = false
                this.exchangeList = []
            } else {
                this.exchangeList = [0, 1]
            }
        },
    },
    mounted() {
        document.title = '赛博筹码游戏房间'
        this.name = sessionStorage.getItem('name')
        this.axios
            .create({
                headers: {
                    token: sessionStorage.getItem('token')
                }
            })
            .get(this.baseURL + '/cyberchip/token/getCyberChip')
            .then((result) => {
                if (result.data.flag === false) {
                    this.snackbar.color = 'error'
                    this.snackbar.text = '数据获取失败，寄了'
                    this.snackbar.show = true
                } else {
                    this.cyberChip = result.data.data
                    for (let memberIndex in this.cyberChip.memberList) {
                        if (this.cyberChip.memberList[memberIndex].name === this.name) {
                            this.index = memberIndex
                            break
                        }
                    }
                }
            })
            .catch((err) => {
                this.snackbar.color = 'error'
                this.snackbar.text = '数据获取失败，寄了'
                this.snackbar.show = true
                console.log(err)
            })
        let _this = this
        this.interval = setInterval(function () {
            _this.getCyberChip()
        }, 1000)
        window.addEventListener('beforeunload', e => this.beforeunloadHandler(e))
        window.addEventListener('unload', e => this.unloadHandler(e))
    },
    unmounted() {
        clearInterval(this.interval)
        window.removeEventListener('beforeunload', e => this.beforeunloadHandler(e))
        window.removeEventListener('unload', e => this.unloadHandler(e))
        this.axios
            .create({
                headers: {
                    token: sessionStorage.getItem('token')
                }
            })
            .post(this.baseURL + '/cyberchip/token/quitRoom')
    },
    methods: {
        beforeunloadHandler(e) {
            this.beforeUnload_time = new Date().getTime();
        },
        unloadHandler(e) {
            this.gap_time = new Date().getTime() - this.beforeUnload_time;
            if (this.gap_time < 10) {
                clearInterval(this.interval)
                window.removeEventListener('beforeunload', e => this.beforeunloadHandler(e))
                window.removeEventListener('unload', e => this.unloadHandler(e))
                this.axios
                    .create({
                        headers: {
                            token: sessionStorage.getItem('token')
                        }
                    })
                    .post(this.baseURL + '/cyberchip/token/quitRoom')
            }
        },
        getCyberChip() {
            this.axios
                .create({
                    headers: {
                        token: sessionStorage.getItem('token')
                    }
                })
                .get(this.baseURL + '/cyberchip/token/getCyberChip')
                .then((result) => {
                    if (result.data.flag === false) {
                        this.snackbar.color = 'error'
                        this.snackbar.text = '数据获取失败，寄了'
                        this.snackbar.show = true
                    } else {
                        this.cyberChip = result.data.data
                    }
                })
                .catch((err) => {
                    this.snackbar.color = 'error'
                    this.snackbar.text = '数据获取失败，寄了'
                    this.snackbar.show = true
                    console.log(err)
                })
        },
        play() {
            if (!this.playForm) {
                return
            }
            this.axios
                .create({
                    headers: {
                        token: sessionStorage.getItem('token')
                    }
                })
                .post(this.baseURL + '/cyberchip/token/play', this.playData)
                .then((result) => {
                    if (result.data.flag === true) {
                        this.snackbar.color = 'success'
                    } else {
                        this.snackbar.color = 'error'
                    }
                    this.snackbar.text = result.data.message
                    this.snackbar.show = true
                    this.cancelPlay()
                })
                .catch((err) => {
                    this.snackbar.color = 'error'
                    this.snackbar.text = '开始游戏失败，寄了'
                    this.snackbar.show = true
                    console.log(err)
                })
            this.cancelPlay()
        },
        cancelPlay() {
            this.playDialogVisible = false
        },
        update() {
            if (this.exchangeList.length > 2) {
                this.exchangeList.shift()
            }
        },
        exchangeMember() {
            if (this.exchangeList.length !== 2) {
                this.exchangeMemberErrorMessageVisible = true
                return
            }
            this.axios
                .create({
                    headers: {
                        token: sessionStorage.getItem('token')
                    }
                })
                .post(this.baseURL + '/cyberchip/token/exchangeMember', { 'index1': this.exchangeList[0], 'index2': this.exchangeList[1] })
                .then((result) => {
                    if (result.data.flag === true) {
                        this.snackbar.color = 'success'
                    } else {
                        this.snackbar.color = 'error'
                    }
                    this.snackbar.text = result.data.message
                    this.snackbar.show = true
                })
                .catch((err) => {
                    this.snackbar.color = 'error'
                    this.snackbar.text = '交换失败，寄了'
                    this.snackbar.show = true
                    console.log(err)
                })
            this.cancelExchangeMember()
        },
        cancelExchangeMember() {
            this.exchangeMemberDialogVisible = false
        },
        required(v) {
            return !!v || '填一下'
        },
        min(v) {
            return v > 0 || '德州不能没有筹码，就像西方不能没有耶路撒冷'
        },
        stop() {
            this.axios
                .create({
                    headers: {
                        token: sessionStorage.getItem('token')
                    }
                })
                .post(this.baseURL + '/cyberchip/token/stop')
                .then((result) => {
                    if (result.data.flag === true) {
                        this.snackbar.color = 'success'
                    } else {
                        this.snackbar.color = 'error'
                    }
                    this.snackbar.text = result.data.message
                    this.snackbar.show = true
                    this.stopDialogVisible = false
                })
                .catch((err) => {
                    this.snackbar.color = 'error'
                    this.snackbar.text = '结束游戏失败，寄了'
                    this.snackbar.show = true
                    console.log(err)
                })
        },
        changeNumber(e) {
            // 下注
            if (this.tempAnte + e < 0) {
                this.tempAnte = 0
            } else if (this.tempAnte + e > this.cyberChip.memberList[this.index].bankRoll) {
                this.tempAnte = this.cyberChip.memberList[this.index].bankRoll
            } else {
                this.tempAnte += e
            }

            // 印钞
            if (e + this.tempBankRoll + this.cyberChip.memberList[this.index].bankRoll < 0) {
                this.tempBankRoll = -this.cyberChip.memberList[this.index].bankRoll
            } else {
                this.tempBankRoll += e
            }

            // 边池
            if (this.tempSidePot + e < 0) {
                this.tempSidePot = 0
            } else if (this.tempSidePot + e > this.cyberChip.pot) {
                this.tempSidePot = this.cyberChip.pot
            } else {
                this.tempSidePot += e
            }
        },
        toZero() {
            this.tempAnte = 0
            this.tempBankRoll = 0
            this.tempSidePot = 0
        },
        ante() {
            if (this.tempAnte === 0) {
                return
            }
            this.axios
                .create({
                    headers: {
                        token: sessionStorage.getItem('token')
                    }
                })
                .post(this.baseURL + '/cyberchip/token/ante', { 'ante': this.tempAnte })
                .then((result) => {
                    if (result.data.flag === true) {
                        this.snackbar.color = 'success'
                    } else {
                        this.snackbar.color = 'error'
                    }
                    this.snackbar.text = result.data.message
                    this.snackbar.show = true
                    this.toZero()
                })
                .catch((err) => {
                    this.snackbar.color = 'error'
                    this.snackbar.text = '下注失败，寄了'
                    this.snackbar.show = true
                    console.log(err)
                })
        },
        changeBankRoll() {
            if (this.tempBankRoll === 0) {
                return
            }
            this.axios
                .create({
                    headers: {
                        token: sessionStorage.getItem('token')
                    }
                })
                .post(this.baseURL + '/cyberchip/token/changeBankRoll', { 'tempBankRoll': this.tempBankRoll })
                .then((result) => {
                    if (result.data.flag === true) {
                        this.snackbar.color = 'success'
                    } else {
                        this.snackbar.color = 'error'
                    }
                    this.snackbar.text = result.data.message
                    this.snackbar.show = true
                    this.toZero()
                })
                .catch((err) => {
                    this.snackbar.color = 'error'
                    this.snackbar.text = '修改资金失败，寄了'
                    this.snackbar.show = true
                    console.log(err)
                })
        },
        addSidePot() {
            if (this.tempSidePot === 0 || this.tempSidePot === this.cyberChip.pot) {
                return
            }
            this.axios
                .create({
                    headers: {
                        token: sessionStorage.getItem('token')
                    }
                })
                .post(this.baseURL + '/cyberchip/token/addSidePot', { 'tempSidePot': this.tempSidePot })
                .then((result) => {
                    if (result.data.flag === true) {
                        this.snackbar.color = 'success'
                    } else {
                        this.snackbar.color = 'error'
                    }
                    this.snackbar.text = result.data.message
                    this.snackbar.show = true
                    this.toZero()
                })
                .catch((err) => {
                    this.snackbar.color = 'error'
                    this.snackbar.text = '添加边池失败，寄了'
                    this.snackbar.show = true
                    console.log(err)
                })
        },
        getRice() {
            if (this.cyberChip.pot === 0) {
                return
            }
            this.axios
                .create({
                    headers: {
                        token: sessionStorage.getItem('token')
                    }
                })
                .post(this.baseURL + '/cyberchip/token/getRice')
                .then((result) => {
                    if (result.data.flag === true) {
                        this.snackbar.color = 'success'
                    } else {
                        this.snackbar.color = 'error'
                    }
                    this.snackbar.text = result.data.message
                    this.snackbar.show = true
                })
                .catch((err) => {
                    this.snackbar.color = 'error'
                    this.snackbar.text = '收米失败，寄了'
                    this.snackbar.show = true
                    console.log(err)
                })
        },
        getSideRice() {
            this.axios
                .create({
                    headers: {
                        token: sessionStorage.getItem('token')
                    }
                })
                .post(this.baseURL + '/cyberchip/token/getSideRice', { 'index': this.chosenSidePot })
                .then((result) => {
                    if (result.data.flag === true) {
                        this.snackbar.color = 'success'
                    } else {
                        this.snackbar.color = 'error'
                    }
                    this.snackbar.text = result.data.message
                    this.snackbar.show = true
                    this.chosenSidePot = 0
                })
                .catch((err) => {
                    this.snackbar.color = 'error'
                    this.snackbar.text = '收米失败，寄了'
                    this.snackbar.show = true
                    console.log(err)
                })
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

.member_card {
    width: 23%;
    margin-left: 1%;
    margin-right: 1%;
    margin-top: 10px;
    margin-bottom: 10px;
    padding: 10px;
}
</style>