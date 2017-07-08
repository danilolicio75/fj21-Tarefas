package br.com.caelum.tarefas.controller;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.caelum.tarefas.dao.JdbcTarefaDao;
import br.com.caelum.tarefas.modelo.Tarefa;

@Controller
public class TarefasController {
	
	private final JdbcTarefaDao dao;
	
	@Autowired
	public TarefasController(JdbcTarefaDao dao) {
		this.dao = dao;
	}
	
	@RequestMapping("novaTarefa")
	public String form() {
		return "tarefa/formulario";		
	}
	@RequestMapping("adicionaTarefa")
	public String adiciona(@Valid Tarefa tarefa, BindingResult r) {
		
		if(r.hasFieldErrors("descricao")) {
	      return "tarefa/formulario";
	    }	
		
		dao.adiciona(tarefa);
		return "tarefa/adiciona";
	}
	@RequestMapping("listaTarefa")
	public String lista(Model model) {
		model.addAttribute("tarefas", dao.lista());
		return "tarefa/lista";
	}
	@RequestMapping("removeTarefa")
	public String remove(Tarefa tarefa) {
		dao.remove(tarefa);
		return "redirect:listaTarefa";
	}
	@RequestMapping("mostraTarefa")
	public String mostra(Long id, Model model) {
		model.addAttribute("tarefa",dao.buscaPorId(id));
		return "tarefa/mostra";
	}
	
	@RequestMapping("alteraTarefa")
	public String altera (Tarefa tarefa){
		dao.altera(tarefa);
		return "redirect:listaTarefa";
	}
	
	@ResponseBody
	@RequestMapping("finalizaTarefa")
	public String finaliza (Long id) {
		dao.finaliza(id);	
		Tarefa t = dao.buscaPorId(id);
		Date data = t.getDataFinalizacao().getTime();
		String formatada = new SimpleDateFormat("dd/MM/yyyy").format(data);
		return formatada;
	}

}
