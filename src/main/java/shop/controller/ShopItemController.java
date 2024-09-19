package shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import shop.domain.model.ShopItem;
import shop.domain.model.User;
import shop.domain.service.ServiceException;
import shop.domain.service.ShopItemRepository;
import shop.domain.service.ShopItemService;
import shop.domain.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/shop")
public class ShopItemController {

    @Autowired
    private ShopItemService shopItemService;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/overview")
    public Iterable<ShopItem> getAll() { return shopItemService.getAllShopItems(); }

    @PutMapping("/add")
    public Iterable<ShopItem> addShopItem(@Valid @RequestBody ShopItem shopItem) {
        shopItemService.addShopItem(shopItem);
        return this.getAll();}

    @PutMapping("/update/{id}")
    public ShopItem updateShopItem(@PathVariable long id, @Valid @RequestBody ShopItem shopItem){
        try {
            shopItemService.checkShopItemExistence(id);
            //if (!shopItemService.getUserById(id)) throw new ServiceException("update", "shopitem.not.exist");
            return shopItemService.updateShopItem(shopItem, id);
        } catch (ServiceException ex){
            throw new ServiceException("update", ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ShopItem deleteShopItem(@PathVariable long id){
        //try {
            return shopItemService.deleteById(id);

        /*} catch (ServiceException ex){
            throw new ServiceException("delete", ex.getMessage());
        }*/
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, ServiceException.class, ResponseStatusException.class})
    public Map<String, String> handleValidationExceptions(Exception ex, Locale locale){
        Map<String, String> errors = new HashMap<>();
        if (ex instanceof MethodArgumentNotValidException) {
            ((MethodArgumentNotValidException)ex).getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = messageSource.getMessage(error.getDefaultMessage(), null, locale);
                errors.put(fieldName, errorMessage);
            });
        }
        else if (ex instanceof ServiceException) {
            String error = messageSource.getMessage(ex.getMessage(), null, locale);
            errors.put(((ServiceException) ex).getAction(), error);
        }
        else {
            String errorMessage = messageSource.getMessage(ex.getCause().getMessage(), null, locale);
            errors.put(((ResponseStatusException)ex).getReason(), errorMessage);
        }
        return errors;
    }

}
