"use client";

import { Todo } from "@/types/todo";
import { Checkbox } from "@/components/ui/checkbox";
import { Button } from "@/components/ui/button";
import { Pencil, Trash2, ChevronDown, ChevronUp } from "lucide-react";
import { useState } from "react";

interface TodoItemProps {
  todo: Todo;
  onUpdate: (id: number, data: { completed: boolean }) => void;
  onDelete: (id: number) => void;
  onEdit: (todo: Todo) => void;
}

export function TodoItem({ todo, onUpdate, onDelete, onEdit }: TodoItemProps) {
  const [isExpanded, setIsExpanded] = useState(false);

  return (
    <div className="border rounded-lg bg-card overflow-hidden">
      <div className="flex items-center justify-between p-4">
        <div className="flex items-center gap-3 flex-1">
          <Checkbox
            checked={todo.completed}
            onCheckedChange={(chk) => onDelete(todo.id)}
          />
          <div>
            <h3
              className={`font-medium ${
                todo.completed ? "line-through text-muted-foreground" : ""
              }`}
            >
              {todo.title}
            </h3>
            <Button
              variant="ghost"
              size="sm"
              className="h-auto p-0 text-muted-foreground hover:text-foreground"
              onClick={() => setIsExpanded(!isExpanded)}
            >
              <span className="text-sm">
                {isExpanded ? "Hide description" : "Show description"}
              </span>
              {isExpanded ? (
                <ChevronUp className="h-4 w-4 ml-1" />
              ) : (
                <ChevronDown className="h-4 w-4 ml-1" />
              )}
            </Button>
          </div>
        </div>
        <div className="flex gap-2">
          <Button variant="ghost" size="icon" onClick={() => onEdit(todo)}>
            <Pencil className="h-4 w-4" />
          </Button>
          <Button variant="ghost" size="icon" onClick={() => onDelete(todo.id)}>
            <Trash2 className="h-4 w-4" />
          </Button>
        </div>
      </div>
      {isExpanded && (
        <div className="px-12 pb-4 text-sm text-muted-foreground">
          {todo.description || "No description provided"}
        </div>
      )}
    </div>
  );
}
