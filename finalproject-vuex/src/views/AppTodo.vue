<template>
  <b-container class="bv-example-row mt-3 text-center">
    <b-row>
      <b-col>
        <todo-header></todo-header>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <todo-input></todo-input>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <todo-list></todo-list>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import TodoHeader from "@/components/todo/TodoHeader.vue";
import TodoInput from "@/components/todo/TodoInput.vue";
import TodoList from "@/components/todo/TodoList.vue";

export default {
  name: "AppTodo",
  components: {
    TodoHeader,
    TodoInput,
    TodoList,
  },
};
</script>

<style scoped>
.underline-steelblue {
  display: inline-block;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0) 70%, rgba(72, 190, 233, 0.3) 30%);
}
</style>
