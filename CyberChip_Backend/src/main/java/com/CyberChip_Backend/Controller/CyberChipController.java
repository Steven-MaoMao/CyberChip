package com.CyberChip_Backend.Controller;

import com.CyberChip_Backend.Config.JWT;
import com.CyberChip_Backend.Model.CyberChip;
import com.CyberChip_Backend.Model.Player;
import com.CyberChip_Backend.Model.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/cyberchip")
public class CyberChipController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final ObjectMapper mapper = new ObjectMapper();

    private CyberChip redisGetCyberChip(String roomName) throws JsonProcessingException {
        String json = stringRedisTemplate.boundHashOps("CyberChipRoomList").get(roomName).toString();
        return mapper.readValue(json, CyberChip.class);
    }

    private void redisSetCyberChip(CyberChip cyberChip) throws JsonProcessingException {
        String json = mapper.writeValueAsString(cyberChip);
        stringRedisTemplate.boundHashOps("CyberChipRoomList").put(cyberChip.getRoomName(), json);
    }

    @GetMapping("/getCyberChipRoomList")
    public Result getCyberChipRoomList() {
        try {
            Result result = new Result();
            result.setFlag(true);
            result.setMessage("获取成功！");
            Map<Object, Object> cyberChipMap = stringRedisTemplate.boundHashOps("CyberChipRoomList").entries();
            Iterator<Object> iterator = cyberChipMap.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next().toString();
                cyberChipMap.put(key, redisGetCyberChip(key));
            }
            result.setData(cyberChipMap);
            return result;
        } catch (Exception e) {
            return new Result();
        }
    }

    @GetMapping("/token/getCyberChip")
    public Result getCyberChip(@RequestHeader("token") String token) {
        try {
            Result result = new Result();
            result.setFlag(true);
            result.setMessage("获取成功！");
            result.setData(redisGetCyberChip(JWT.parseToken(token).get("roomName").toString()));
            return result;
        } catch (Exception e) {
            return new Result();
        }
    }

    @PostMapping("/createRoom")
    public Result createRoom(@RequestBody Map<String, Object> map) {
        try {
            Result result = new Result();
            if (stringRedisTemplate.boundHashOps("CyberChipRoomList").hasKey(map.get("roomName").toString())) {
                result.setMessage("房间名重复，创建失败！");
                return result;
            }
            CyberChip cyberChip = new CyberChip();
            cyberChip.setRoomName(map.get("roomName").toString());
            cyberChip.setRoomManager(map.get("roomManager").toString());
            cyberChip.setRoomPassword(map.get("roomPassword").toString());
            Player player = new Player();
            player.setName(map.get("roomManager").toString());
            List<Player> playerList = new ArrayList<>();
            playerList.add(player);
            cyberChip.setMemberList(playerList);
            redisSetCyberChip(cyberChip);
            String token = JWT.createToken(map.get("roomName").toString(), map.get("roomManager").toString());
            result.setFlag(true);
            result.setMessage("创建成功！");
            result.setData(token);
            return result;
        } catch (Exception e) {
            return new Result();
        }
    }

    @PostMapping("/joinRoom")
    public Result joinRoom(@RequestBody Map<String, Object> map) {
        try {
            CyberChip cyberChipRoom = redisGetCyberChip(map.get("roomName").toString());
            Result result = new Result();
            if (!cyberChipRoom.getRoomPassword().equals(map.get("roomPassword"))) {
                result.setMessage("密码错误！");
                return result;
            }
            for (int i=0;i<cyberChipRoom.getMemberList().size();i++) {
                if (cyberChipRoom.getMemberList().get(i).getName().equals(map.get("name"))) {
                    result.setMessage("昵称重复！");
                    return result;
                }
            }
            Player player = new Player();
            player.setName(map.get("name").toString());
            cyberChipRoom.getMemberList().add(player);
            redisSetCyberChip(cyberChipRoom);
            String token = JWT.createToken(map.get("roomName").toString(), map.get("name").toString());
            result.setFlag(true);
            result.setMessage("加入成功！");
            result.setData(token);
            return result;
        } catch (Exception e) {
            return new Result();
        }
    }

    @PostMapping("/token/quitRoom")
    public Result quitRoom(@RequestHeader("token") String token) {
        try {
            String roomName = JWT.parseToken(token).get("roomName").toString();
            String name = JWT.parseToken(token).get("name").toString();
            CyberChip cyberChipRoom = redisGetCyberChip(roomName);
            if (cyberChipRoom.getMemberList().size() == 1) {
                stringRedisTemplate.boundHashOps("CyberChipRoomList").delete(roomName);
            } else {
                for (int i = 0; i < cyberChipRoom.getMemberList().size(); i++) {
                    if (cyberChipRoom.getMemberList().get(i).getName().equals(name)) {
                        cyberChipRoom.setChipCount(cyberChipRoom.getChipCount() - cyberChipRoom.getMemberList().get(i).getBankRoll() - cyberChipRoom.getMemberList().get(i).getAllAnte());
                        cyberChipRoom.setPot(cyberChipRoom.getPot() - cyberChipRoom.getMemberList().get(i).getAllAnte());
                        cyberChipRoom.getMemberList().remove(i);
                        break;
                    }
                }
                if (cyberChipRoom.getRoomManager().equals(name)) {
                    cyberChipRoom.setRoomManager(cyberChipRoom.getMemberList().get(0).getName());
                }
                redisSetCyberChip(cyberChipRoom);
            }
            Result result = new Result();
            result.setFlag(true);
            result.setMessage("退出成功！");
            return result;
        } catch (Exception e) {
            return new Result();
        }
    }

    @PostMapping("/token/exchangeMember")
    public Result exchangeMember(@RequestHeader("token") String token, @RequestBody Map<String, Integer> map) {
        try {
            CyberChip cyberChipRoom = redisGetCyberChip(JWT.parseToken(token).get("roomName").toString());
            Collections.swap(cyberChipRoom.getMemberList(), map.get("index1"), map.get("index2"));
            redisSetCyberChip(cyberChipRoom);
            Result result = new Result();
            result.setFlag(true);
            result.setMessage("交换成功！");
            return result;
        } catch (Exception e) {
            return new Result();
        }
    }

    @PostMapping("/token/play")
    public Result play(@RequestHeader("token") String token, @RequestBody Map<String, Integer> map) {
        try {
            CyberChip cyberChipRoom = redisGetCyberChip(JWT.parseToken(token).get("roomName").toString());
            int chipCount = map.get("chipCount");
            int bankRoll = chipCount / cyberChipRoom.getMemberList().size();
            int pot = chipCount - bankRoll * cyberChipRoom.getMemberList().size();
            cyberChipRoom.setChipCount(chipCount);
            cyberChipRoom.setPot(pot);
            cyberChipRoom.setSidePotList(new ArrayList<>());
            for (int i=0;i<cyberChipRoom.getMemberList().size();i++) {
                cyberChipRoom.getMemberList().get(i).setBankRoll(bankRoll);
                cyberChipRoom.getMemberList().get(i).setThisRoundAnte(0);
                cyberChipRoom.getMemberList().get(i).setAllAnte(0);
            }
            cyberChipRoom.setGaming(true);
            redisSetCyberChip(cyberChipRoom);
            Result result = new Result();
            result.setFlag(true);
            result.setMessage("开始游戏成功！");
            return result;
        } catch (Exception e) {
            return new Result();
        }
    }

    @PostMapping("/token/stop")
    public Result stop(@RequestHeader("token") String token) {
        try {
            CyberChip cyberChipRoom = redisGetCyberChip(JWT.parseToken(token).get("roomName").toString());
            cyberChipRoom.setGaming(false);
            cyberChipRoom.setChipCount(0);
            cyberChipRoom.setPot(0);
            cyberChipRoom.setSidePotList(null);
            for (int i=0;i<cyberChipRoom.getMemberList().size();i++) {
                cyberChipRoom.getMemberList().get(i).setBankRoll(0);
                cyberChipRoom.getMemberList().get(i).setThisRoundAnte(0);
                cyberChipRoom.getMemberList().get(i).setAllAnte(0);
            }
            redisSetCyberChip(cyberChipRoom);
            Result result = new Result();
            result.setFlag(true);
            result.setMessage("结束游戏成功！");
            return result;
        } catch (Exception e) {
            return new Result();
        }
    }

    @PostMapping("/token/ante")
    public Result ante(@RequestHeader("token") String token, @RequestBody Map<String, Integer> map) {
        try {
            String roomName = JWT.parseToken(token).get("roomName").toString();
            String name = JWT.parseToken(token).get("name").toString();
            int ante = map.get("ante");
            CyberChip cyberChipRoom = redisGetCyberChip(roomName);
            cyberChipRoom.setPot(cyberChipRoom.getPot() + ante);
            for (int i = 0; i < cyberChipRoom.getMemberList().size(); i++) {
                if (cyberChipRoom.getMemberList().get(i).getName().equals(name)) {
                    cyberChipRoom.getMemberList().get(i).setThisRoundAnte(ante);
                    cyberChipRoom.getMemberList().get(i).setAllAnte(cyberChipRoom.getMemberList().get(i).getAllAnte() + ante);
                    cyberChipRoom.getMemberList().get(i).setBankRoll(cyberChipRoom.getMemberList().get(i).getBankRoll() - ante);
                    break;
                }
            }
            redisSetCyberChip(cyberChipRoom);
            Result result = new Result();
            result.setFlag(true);
            result.setMessage("下注 " + ante + " 成功！");
            return result;
        } catch (Exception e) {
            return new Result();
        }
    }

    @PostMapping("/token/changeBankRoll")
    public Result changeBankRoll(@RequestHeader("token") String token, @RequestBody Map<String, Integer> map) {
        try {
            String roomName = JWT.parseToken(token).get("roomName").toString();
            String name = JWT.parseToken(token).get("name").toString();
            int tempBankRoll = map.get("tempBankRoll");
            CyberChip cyberChipRoom = redisGetCyberChip(roomName);
            cyberChipRoom.setChipCount(cyberChipRoom.getChipCount() + tempBankRoll);
            for (int i = 0; i < cyberChipRoom.getMemberList().size(); i++) {
                if (cyberChipRoom.getMemberList().get(i).getName().equals(name)) {
                    cyberChipRoom.getMemberList().get(i).setBankRoll(cyberChipRoom.getMemberList().get(i).getBankRoll() + tempBankRoll);
                    break;
                }
            }
            redisSetCyberChip(cyberChipRoom);
            Result result = new Result();
            result.setFlag(true);
            result.setMessage("修改资金 " + tempBankRoll + " 成功！");
            return result;
        } catch (Exception e) {
            return new Result();
        }
    }

    @PostMapping("/token/addSidePot")
    public Result addSidePot(@RequestHeader("token") String token, @RequestBody Map<String, Integer> map) {
        try {
            CyberChip cyberChipRoom = redisGetCyberChip(JWT.parseToken(token).get("roomName").toString());
            cyberChipRoom.getSidePotList().add(map.get("tempSidePot"));
            cyberChipRoom.setPot(cyberChipRoom.getPot() - map.get("tempSidePot"));
            redisSetCyberChip(cyberChipRoom);
            Result result = new Result();
            result.setFlag(true);
            result.setMessage("添加边池 边池" + cyberChipRoom.getSidePotList().size() + ":" + map.get("tempSidePot") + " 成功！");
            return result;
        } catch (Exception e) {
            return new Result();
        }
    }

    @PostMapping("/token/getRice")
    public Result getRice(@RequestHeader("token") String token) {
        try {
            String roomName = JWT.parseToken(token).get("roomName").toString();
            String name = JWT.parseToken(token).get("name").toString();
            CyberChip cyberChipRoom = redisGetCyberChip(roomName);
            int rice = cyberChipRoom.getPot();
            for (int i=0;i<cyberChipRoom.getMemberList().size();i++) {
                if (cyberChipRoom.getMemberList().get(i).getName().equals(name)) {
                    cyberChipRoom.getMemberList().get(i).setBankRoll(cyberChipRoom.getMemberList().get(i).getBankRoll() + rice);
                    cyberChipRoom.setPot(0);
                }
                cyberChipRoom.getMemberList().get(i).setThisRoundAnte(0);
                cyberChipRoom.getMemberList().get(i).setAllAnte(0);
            }
            redisSetCyberChip(cyberChipRoom);
            Result result = new Result();
            result.setFlag(true);
            result.setMessage("收米大成功！ +" + rice);
            return result;
        } catch (Exception e) {
            return new Result();
        }
    }

    @PostMapping("/token/getSideRice")
    public Result getSideRice(@RequestHeader("token") String token, @RequestBody Map<String, Integer> map) {
        try {
            String roomName = JWT.parseToken(token).get("roomName").toString();
            String name = JWT.parseToken(token).get("name").toString();
            int index = map.get("index");
            CyberChip cyberChipRoom = redisGetCyberChip(roomName);
            int rice = cyberChipRoom.getSidePotList().get(index);
            for (int i=0;i<cyberChipRoom.getMemberList().size();i++) {
                if (cyberChipRoom.getMemberList().get(i).getName().equals(name)) {
                    cyberChipRoom.getMemberList().get(i).setBankRoll(cyberChipRoom.getMemberList().get(i).getBankRoll() + rice);
                    cyberChipRoom.getSidePotList().remove(index);
                    break;
                }
            }
            redisSetCyberChip(cyberChipRoom);
            Result result = new Result();
            result.setFlag(true);
            result.setMessage("收米小成功！ +" + rice);
            return result;
        } catch (Exception e) {
            return new Result();
        }
    }

    @DeleteMapping("/deleteRoom/{roomName}")
    public Result deleteRoom(@PathVariable String roomName) {
        try {
            stringRedisTemplate.boundHashOps("CyberChipRoomList").delete(roomName);
            Result result = new Result();
            result.setFlag(true);
            result.setMessage("删除成功！");
            return result;
        } catch (Exception e) {
            return new Result();
        }
    }
}
