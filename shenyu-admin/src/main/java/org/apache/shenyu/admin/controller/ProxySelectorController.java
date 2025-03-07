/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shenyu.admin.controller;

import org.apache.shenyu.admin.mapper.PluginMapper;
import org.apache.shenyu.admin.model.dto.ProxySelectorDTO;
import org.apache.shenyu.admin.model.page.CommonPager;
import org.apache.shenyu.admin.model.page.PageParameter;
import org.apache.shenyu.admin.model.query.ProxySelectorQuery;
import org.apache.shenyu.admin.model.result.ShenyuAdminResult;
import org.apache.shenyu.admin.model.vo.ProxySelectorVO;
import org.apache.shenyu.admin.service.ProxySelectorService;
import org.apache.shenyu.admin.utils.ShenyuResultMessage;
import org.apache.shenyu.admin.validation.annotation.Existed;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping("/proxy-selector")
public class ProxySelectorController {

    private final ProxySelectorService proxySelectorService;

    public ProxySelectorController(final ProxySelectorService proxySelectorService) {

        this.proxySelectorService = proxySelectorService;
    }

    /**
     * page proxy selector.
     *
     * @param name        name
     * @param currentPage currentPage
     * @param pageSize    pageSize
     * @return {@linkplain ShenyuAdminResult}
     */
    @GetMapping("")
    public ShenyuAdminResult queryProxySelector(final String name, @NotNull final Integer currentPage,
                                                @NotNull final Integer pageSize) {

        CommonPager<ProxySelectorVO> commonPager = proxySelectorService
                .listByPage(new ProxySelectorQuery(name, new PageParameter(currentPage, pageSize)));
        return ShenyuAdminResult.success(ShenyuResultMessage.QUERY_SUCCESS, commonPager);
    }

    /**
     * create proxy selector.
     *
     * @param proxySelectorDTO proxySelectorDTO
     * @return {@linkplain ShenyuAdminResult}
     */
    @PostMapping("")
    public ShenyuAdminResult createProxySelector(@Valid @RequestBody final ProxySelectorDTO proxySelectorDTO) {

        return ShenyuAdminResult.success(proxySelectorService.createOrUpdate(proxySelectorDTO));
    }

    /**
     * update proxy selector.
     *
     * @param id               id
     * @param proxySelectorDTO proxySelectorDTO
     * @return {@linkplain ShenyuAdminResult}
     */
    @PutMapping("/{id}")
    public ShenyuAdminResult updateProxySelector(@PathVariable("id")
                                                 @Existed(message = "proxy selector is not existed",
                                                         provider = PluginMapper.class) final String id,
                                                 @Valid @RequestBody final ProxySelectorDTO proxySelectorDTO) {

        proxySelectorDTO.setId(id);
        return ShenyuAdminResult.success(proxySelectorService.createOrUpdate(proxySelectorDTO));
    }

    /**
     * batch delete.
     *
     * @param ids id list
     * @return {@linkplain ShenyuAdminResult}
     */
    @DeleteMapping("/batch")
    public ShenyuAdminResult deleteProxySelectors(@NotEmpty @RequestBody final List<@NotBlank String> ids) {

        return ShenyuAdminResult.success(proxySelectorService.delete(ids));
    }
}
