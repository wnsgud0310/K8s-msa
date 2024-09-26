package org.msa.item.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.msa.item.dto.ItemDTO;
import org.msa.item.dto.ResponseDTO;
import org.msa.item.dto.constant.ItemType;
import org.msa.item.exception.ApiException;
import org.msa.item.service.ItemService;
import org.msa.item.valid.ItemTypeValid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="v1/item")
@Slf4j
@RequiredArgsConstructor
@Validated
public class ItemController {

    private final ItemService itemService;

    /**
     *
     * http://localhost:8080/v1/item/add
     *
     * {
     *     "id" : "1234567890",
     *     "name" : "물품2",
     *     "description" : "물품설명2",
     *     "count" : 10
     * }
     *
     *
     *
     *
     *
     * http://localhost:8080/v1/item/add/F
     *
     * {
     *      *     "id" : "1234567890",
     *      *     "name" : "물품2",
     *      *     "description" : "물품설명2",
     *      *     "count" : 10
     *      * }
     *
     */

    @RequestMapping(value="/add/{itemType}", method= RequestMethod.POST)
    public ResponseEntity<ResponseDTO> add(@Valid @RequestBody ItemDTO itemDTO, @ItemTypeValid @PathVariable String itemType) throws Exception{
        ResponseDTO.ResponseDTOBuilder responseBuilder = ResponseDTO.builder();

        /*
        log.debug("path.variable itemType = {}", itemType);
        boolean hasItemType = false;
        ItemType[] itemTypeList = ItemType.values();
        for(ItemType i : itemTypeList) {
            hasItemType = i.hasItemCd(itemType);

            if(hasItemType) break;
        }

        if(!hasItemType) {
            responseBuilder.code("500").message("invalid itemType .[" + itemType + "]");
            return ResponseEntity.ok(responseBuilder.build());
        }else {
            itemDTO.setItemType(itemType);
        }


        try {
            Integer.parseInt("test");
        }catch(Exception e) {
            throw new ApiException("test에러");
        }
        */

        itemDTO.setItemType(itemType);

        itemService.insertItem(itemDTO);
        log.debug("request add item id = {}", itemDTO.getId());

        responseBuilder.code("200").message("success");
        return ResponseEntity.ok(responseBuilder.build());
    }
}
