package com.br.pruma.controller;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
@Api(tags = "Endpoint do Usuario", description = "Endpoint usado para o usuario")
public class UsuarioController {
}
