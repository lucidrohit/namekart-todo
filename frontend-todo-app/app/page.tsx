"use client";

import { useState, useEffect } from "react";
import { api } from "@/lib/axios";
import { Todo, CreateTodoInput, UpdateTodoInput } from "@/types/todo";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { Button } from "@/components/ui/button";
import { TodoItem } from "@/components/todo-item";
import { EditTodoDialog } from "@/components/edit-todo-dialog";
import { useToast } from "@/hooks/use-toast";
import { Loader2 } from "lucide-react";

export default function Home() {
  const [todos, setTodos] = useState<Todo[]>([]);
  const [newTodo, setNewTodo] = useState("");
  const [newDescription, setNewDescription] = useState("");
  const [loading, setLoading] = useState(true);
  const [selectedTodo, setSelectedTodo] = useState<Todo | null>(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const { toast } = useToast();

  const fetchTodos = async () => {
    try {
      const response = await api.get<Todo[]>("/todos");
      setTodos(response.data);
    } catch (error) {
      toast({
        title: "Error",
        description: "Failed to fetch todos",
        variant: "destructive",
      });
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchTodos();
  }, []);

  const createTodo = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!newTodo.trim()) return;

    try {
      const input: CreateTodoInput = {
        title: newTodo,
        description: newDescription,
      };
      const response = await api.post<Todo>("/todos", input);
      setTodos([...todos, response.data]);
      setNewTodo("");
      setNewDescription("");
      toast({
        title: "Success",
        description: "Todo created successfully",
      });
    } catch (error) {
      toast({
        title: "Error",
        description: "Failed to create todo",
        variant: "destructive",
      });
    }
  };

  const updateTodo = async (id: number, data: UpdateTodoInput) => {
    try {
      const response = await api.put<Todo>(`/todos/${id}`, data);
      setTodos(todos.map((todo) => (todo.id === id ? response.data : todo)));
      toast({
        title: "Success",
        description: "Todo updated successfully",
      });
    } catch (error) {
      toast({
        title: "Error",
        description: "Failed to update todo",
        variant: "destructive",
      });
    }
  };

  const deleteTodo = async (id: number) => {
    try {
      await api.delete(`/todos/${id}`);
      setTodos(todos.filter((todo) => todo.id !== id));
      toast({
        title: "Success",
        description: "Todo deleted successfully",
      });
    } catch (error) {
      toast({
        title: "Error",
        description: "Failed to delete todo",
        variant: "destructive",
      });
    }
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <Loader2 className="h-8 w-8 animate-spin" />
      </div>
    );
  }

  return (
    <div className="max-w-2xl mx-auto p-6">
      <h1 className="text-3xl font-bold mb-8">Todo List</h1>

      <form onSubmit={createTodo} className="space-y-4 mb-8">
        <div>
          <Input
            value={newTodo}
            onChange={(e) => setNewTodo(e.target.value)}
            placeholder="Add a new todo..."
            className="w-full"
          />
        </div>
        <div className="flex gap-2">
          <Textarea
            value={newDescription}
            onChange={(e) => setNewDescription(e.target.value)}
            placeholder="Add a description (optional)..."
            className="flex-1"
            rows={3}
          />
          <Button type="submit" className="self-end">
            Add Todo
          </Button>
        </div>
      </form>

      <div className="space-y-3">
        {todos.map((todo) => (
          <TodoItem
            key={todo.id}
            todo={todo}
            onUpdate={updateTodo}
            onDelete={deleteTodo}
            onEdit={(todo) => {
              setSelectedTodo(todo);
              setDialogOpen(true);
            }}
          />
        ))}
      </div>

      <EditTodoDialog
        todo={selectedTodo}
        open={dialogOpen}
        onOpenChange={setDialogOpen}
        onUpdate={updateTodo}
      />
    </div>
  );
}
