package com.medicine.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medicine.springboot.common.Result;
import com.medicine.springboot.entity.Client;
import com.medicine.springboot.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @RequestMapping("/getByCno")
    public Object getByMno(Integer cno)
    {
        return clientService.getByCno(cno);
    }

    @GetMapping("/find")
    public Result getAllUser() throws SQLException {
        List<Client> clients = clientService.list(new QueryWrapper<Client>().orderByDesc("cno"));
        return Result.success(clients);
    }

    @PostMapping("/save")
    public boolean addClient(@RequestBody Client client) {
        return clientService.save(client);
    }

    @PutMapping("/update/{cno}")
    public Client updateClient(@PathVariable Integer cno, @RequestBody Client client) {
        client.setCno(cno);
        return clientService.update(client);
    }

    @DeleteMapping("/delete/{cno}")
    public void deleteClient(@PathVariable Integer cno) {
        clientService.delete(cno);
    }

    @DeleteMapping("/deleteBatch")
    public ResponseEntity<?> deleteBatch(@RequestBody List<Integer> cnos) {
        // 批量删除逻辑
        for (Integer cno : cnos) {
            clientService.delete(cno);// 执行单个删除操作
        }
        // 创建响应的 JSON 对象
        Map<String, String> response = new HashMap<>();
        response.put("code", "200");
        response.put("message", "批量删除成功");

        return ResponseEntity.ok(response); // 返回标准的 JSON 响应
    }

    @PutMapping("/reorder")
    public Result reorderClients(@RequestBody List<Client> clients) throws SQLException {
        // 遍历药品列表并更新药品编号
        for (int i = 0; i < clients.size(); i++) {
            Client client = clients.get(i);
            // 设置新的药品编号，假设从 1 开始
            client.setCno(i + 1);
            // 更新药品信息
            clientService.updateById(client);
        }
        return Result.success("药品编号重新排序成功");
    }

    @GetMapping("/search")
    public Result searchClients(
            @RequestParam(required = false) String cno,
            @RequestParam(required = false) String cname,
            @RequestParam(required = false) String csex,
            @RequestParam(required = false) String caddress,
            @RequestParam(required = false) String cphone,
            @RequestParam(required = false) String csymptom,
            @RequestParam(required = false) String mno,
            @RequestParam(required = false) String ano,
            @RequestParam(required = false) String cdate,
            @RequestParam(required = false) String cremark
            ) throws SQLException {

        QueryWrapper<Client> queryWrapper = new QueryWrapper<>();

        // 根据传入的参数添加查询条件
        if (cno != null && !cno.isEmpty()) {
            queryWrapper.eq("cno", cno);
        }
        if (cname != null && !cname.isEmpty()) {
            queryWrapper.like("cname", cname); // 使用like匹配名称
        }
        if (csex != null && !csex.isEmpty()) {
            queryWrapper.eq("csex", csex);
        }
        if (caddress != null && !caddress.isEmpty()) {
            queryWrapper.like("caddress", caddress); // 使用like匹配功效
        }
        if (cphone != null && !cphone.isEmpty()) {
            queryWrapper.like("cphone", cphone);
        }
        if (csymptom != null && !csymptom.isEmpty()) {
            queryWrapper.like("csymptom", csymptom);
        }
        if (mno != null && !mno.isEmpty()) {
            queryWrapper.eq("mno", mno);
        }
        if (ano != null && !ano.isEmpty()) {
            queryWrapper.eq("ano", ano);
        }
        if (cdate != null && !cdate.isEmpty()) {
            queryWrapper.like("cdate", cdate);
        }
        if (cremark != null && !cremark.isEmpty()) {
            queryWrapper.like("cremark", cremark);
        }



        // 根据构建的条件查询药品
        List<Client> clients = clientService.list(queryWrapper);

        // 返回结果
        return Result.success(clients);
    }
    
}