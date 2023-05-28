package com.example.backend.resources;

import com.example.backend.entities.Category;
import com.example.backend.services.CategoryService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/category")
public class CategoryResource {
    @Inject
    private CategoryService categoryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> all() {return this.categoryService.allCategories();}

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Category addCategory(@Valid Category category) {return this.categoryService.addCategory(category);}

    @GET
    @Path("/{categoryName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category findCategory(@PathParam("categoryName") String categoryName) {return this.categoryService.findCategory(categoryName);}

    @DELETE
    @Path("/{categoryName}")
    public boolean deleteCategory(@PathParam("categoryName") String categoryName) {return this.categoryService.deleteCategory(categoryName);}

    @PATCH
    @Path("/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category updateCategory(@PathParam("categoryId") int categoryId, @Valid Category category) {return this.categoryService.updateCategory(categoryId, category);}
}
