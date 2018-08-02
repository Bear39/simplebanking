package com.simplebanking.controller;

import com.simplebanking.domain.Receiver;
import com.simplebanking.domain.User;
import com.simplebanking.service.TransactionService;
import com.simplebanking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/addReceiver", method = RequestMethod.GET)
    public String addReceiver(Model model, Principal principal) {
        List<Receiver> receiverList = transactionService.findReceiverList(principal);

        Receiver receiver = new Receiver();

        model.addAttribute("receiverList", receiverList);
        model.addAttribute("receiver", receiver);

        return "addReceiver";
    }

    @RequestMapping(value = "/addReceiver/save", method = RequestMethod.POST)
    public String receiverPost(@ModelAttribute("receiver") Receiver receiver, Principal principal) {

        User user = userService.findByUsername(principal.getName());
        User receiverUser = userService.findByUsername(receiver.getName());
        receiver.setUser(user);
        receiver.setAccountNumber(receiverUser.getAccount().getAccountNumber()+"");
        transactionService.saveReceiver(receiver);

        return "redirect:/transfer/addReceiver";
    }

    @RequestMapping(value = "/addReceiver/delete", method = RequestMethod.GET)
    @Transactional
    public String receiverDelete(@RequestParam(value = "receiverName") String receiverName, Model model, Principal principal){

        transactionService.deleteReceiverByName(receiverName);

        List<Receiver> receiverList = transactionService.findReceiverList(principal);

        Receiver receiver= new Receiver();
        model.addAttribute("receiverList", receiverList);
        model.addAttribute("receiver", receiver);


        return "addReceiver";
    }

    @RequestMapping(value = "/otherAccount",method = RequestMethod.GET)
    public String otherAccount(Model model, Principal principal) {
        List<Receiver> receiverList = transactionService.findReceiverList(principal);

        model.addAttribute("receiverList", receiverList);

        return "otherAccount";
    }

    @RequestMapping(value = "/otherAccount",method = RequestMethod.POST)
    public String otherAccountPost(@ModelAttribute("receiverName") String receiverName, @ModelAttribute("amount") String amount, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        User receiverUser = userService.findByUsername(receiverName);
        transactionService.toOtherAccountTransfer(amount, user.getAccount(), user.getUsername(), receiverName, receiverUser.getAccount());

        return "redirect:/home";
    }
}
