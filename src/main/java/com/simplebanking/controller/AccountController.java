package com.simplebanking.controller;

import com.simplebanking.domain.Account;
import com.simplebanking.domain.Transaction;
import com.simplebanking.domain.User;
import com.simplebanking.service.AccountService;
import com.simplebanking.service.TransactionService;
import com.simplebanking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping("/data")
    public String data(Model model, Principal principal) {
        List<Transaction> transactionList = transactionService.findTransactionList(principal.getName());

        User user = userService.findByUsername(principal.getName());
        Account account = user.getAccount();

        model.addAttribute("account", account);
        model.addAttribute("transactionList", transactionList);

        return "data";
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.GET)
    public String deposit(Model model) {
        model.addAttribute("amount", "");

        return "deposit";
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public String depositPOST(@ModelAttribute("amount") String amount, Principal principal) {
        accountService.deposit(Double.parseDouble(amount), principal);

        return "redirect:/home";
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.GET)
    public String withdraw(Model model) {
        model.addAttribute("amount", "");

        return "withdraw";
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public String withdrawPOST(@ModelAttribute("amount") String amount, Principal principal) {
        accountService.withdraw(Double.parseDouble(amount), principal);

        return "redirect:/home";
    }
}
