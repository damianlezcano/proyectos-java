package com.company.foo.web;

import java.util.Map;

import com.company.foo.bo.BO;
import com.company.foo.bo.DefaultBO;
import com.company.foo.model.Entity;
import com.company.foo.util.ClassFinder;
import com.company.foo.util.Response;

public class DefaultController implements Controller {

	private BO bo;
	
	public DefaultController(Class<?> clazz){
		try {
			bo = (BO) Class.forName(ClassFinder.PACK_FULL_PATH + ClassFinder.PACK_BO_TYPE + "." + clazz.getSimpleName() + "BO").newInstance();
		} catch (Exception e) {
			bo = new DefaultBO(clazz);
		}
	}
	
	@Override
	public Response index(Class<?> clazz, Long id, Map<String,Object> params){
		System.out.println("index ["+this.getClass().getSimpleName()+"] - params:" + params);
		return Response.ok(bo.list(clazz));
	}

	@Override
	public Response show(Class<?> clazz, Long id, Map<String,Object> params){
		System.out.println("show ["+this.getClass().getSimpleName()+"] - params:" + params);
		return Response.ok(bo.get(clazz,id));
	}

	@Override
	public Response create(Class<?> clazz, Long id, Map<String,Object> params){
		System.out.println("create ["+this.getClass().getSimpleName()+"] - params:" + params);
		try {
			return Response.ok(clazz.newInstance());
		} catch (Exception e) {
			return Response.fail();
		}
	}
	
	@Override
	public Response save(Entity entity, Map<String,Object> params){
		System.out.println("save ["+this.getClass().getSimpleName()+"] - params:" + params);
		bo.save(entity);
		return Response.ok().redirect(entity.getClass().getSimpleName(),"show", entity.getId(), params).message("Creado exitosamente");
	}
	
	@Override
	public Response edit(Class<?> clazz, Long id, Map<String,Object> params){
		System.out.println("edit ["+this.getClass().getSimpleName()+"] - params:" + params);
		return Response.ok(bo.get(clazz,id));
	}

	@Override
	public Response update(Entity entity, Map<String,Object> params){
		System.out.println("update ["+this.getClass().getSimpleName()+"] - params:" + params);
		bo.save(entity);
		return Response.ok().redirect(entity.getClass().getSimpleName(),"show", entity.getId(), params).message("Modificado exitosamente");
	}
	
	@Override
	public Response remove(Class<?> clazz, Long id, Map<String,Object> params){
		System.out.println("remove ["+this.getClass().getSimpleName()+"] - params:" + params);
		Entity entity = (Entity)bo.get(clazz,id);
		bo.remove(clazz,id);
		return Response.ok(entity).message("Eliminado correctamente");
	}
}