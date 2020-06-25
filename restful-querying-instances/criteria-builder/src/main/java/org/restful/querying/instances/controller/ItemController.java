/**
 * Copyright 2020 the project restful-querying-instances authors
 * and the original author or authors annotated by {@author}
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.restful.querying.instances.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.restful.querying.instances.model.entity.Item;
import org.restful.querying.instances.model.payload.ItemDetail;
import org.restful.querying.instances.repository.ItemRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @project restful-querying-instances
 * @created 24.05.2020 07:54
 * <p>
 * @author Alexander A. Kropotin
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@CrossOrigin(value = "/**")
@RestController
@RequestMapping("/item")
public class ItemController {

    ItemRepository itemRepository;

    PagedResourcesAssembler<Item> assembler;

    @GetMapping(
            value = "/{uid}",
            produces = "application/json"
    )
    public ResponseEntity<ItemDetail> find(
            @PathVariable(
                    name = "uid"
            ) Long uid) {
        Item someItem = this.itemRepository.findById(uid)
                .orElse(null);

        return ResponseEntity
                .status(
                        someItem == null
                                ? HttpStatus.NOT_FOUND
                                : HttpStatus.OK
                )
                .body(ItemDetail.getInstance(someItem));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(
            produces = "application/json"
    )
    public ResponseEntity<PagedResources<ItemDetail>> findFiltered(
            @RequestParam(
                    value = "uid",
                    required = false
            ) List<Long> uid,
            Pageable pageable) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        this.assembler.toResource(
                                itemRepository.findByIdIn(uid),
                                ItemDetail::getInstance
                ));
    }
}
