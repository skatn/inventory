package com.namsu.inventoryspring.domain.category.api;

import com.namsu.inventoryspring.domain.category.application.CategoryService;
import com.namsu.inventoryspring.domain.category.domain.Category;
import com.namsu.inventoryspring.domain.category.dto.CreateCategoryForm;
import com.namsu.inventoryspring.domain.category.dto.DeleteCategoryForm;
import com.namsu.inventoryspring.domain.category.dto.UpdateCategoryForm;
import com.namsu.inventoryspring.domain.member.domain.Member;
import com.namsu.inventoryspring.global.auth.PrincipalDetails;
import com.namsu.inventoryspring.global.util.ScriptUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/category")
    public String category(@AuthenticationPrincipal PrincipalDetails principalDetails,
                           @ModelAttribute("form") CreateCategoryForm form,
                           Model model) {

        List<Category> categories = categoryService.getCategories(principalDetails.getMember());

        model.addAttribute("updateForm", new UpdateCategoryForm());
        model.addAttribute("categories", categories);
        return "category/category";
    }

    @PostMapping("/category/new")
    public void newCategory(@AuthenticationPrincipal PrincipalDetails principalDetails,
                            @Validated @ModelAttribute("form") CreateCategoryForm form,
                            BindingResult bindingResult,
                            HttpServletResponse response) throws IOException {

        if (bindingResult.hasErrors()) {
            String message = "";
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                message += fieldError.getDefaultMessage() + "\n";
            }
            ScriptUtils.alertAndBackPage(response, message.substring(0, message.length() - 1));
            return;
        }

        Member member = principalDetails.getMember();
        categoryService.addCategory(member, form);
        response.sendRedirect("/category");
    }


    @PostMapping("/category/update")
    public void updateCategory(@AuthenticationPrincipal PrincipalDetails principalDetails,
                               @Validated @ModelAttribute("updateForm") UpdateCategoryForm form,
                               BindingResult bindingResult,
                               HttpServletResponse response) throws IOException {

        if (bindingResult.hasErrors()) {
            String message = "";
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                message += fieldError.getDefaultMessage() + "\n";
            }
            ScriptUtils.alertAndBackPage(response, message.substring(0, message.length() - 1));
            return;
        }

        Member member = principalDetails.getMember();
        categoryService.updateCategory(member, form);
        response.sendRedirect("/category");
    }

    @PostMapping("/category/delete")
    public String deleteCategory(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                 @ModelAttribute DeleteCategoryForm form) {

        categoryService.deleteCategory(principalDetails.getMember(), form);

        return "redirect:/category";
    }
}
