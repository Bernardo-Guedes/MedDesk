import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";


interface ListItemProps {
  nome: string;
  cargo?: string;
}

function ListItem({ nome, cargo }: ListItemProps) {
  return (
    <div className="rounded-full flex justify-between items-center px-5 py-3 bg-white max-h-max mx-10 mb-4">
      <div className="flex gap-4 items-center">
        <Avatar className="size-14 shrink-0 shadow-sm border-2 border-destaque">
          <AvatarImage src="https://randomuser.me/api/portraits/women/32.jpg" />
          <AvatarFallback className="bg-gray-200 text-gray-500 text-5xl font-semibold">
            {nome
              .split(" ")
              .map((parte) => parte[0])
              .slice(0, 2)
              .join("")
              .toUpperCase()}
          </AvatarFallback>
        </Avatar>
        <div className="flex flex-col">
            <span className="font-semibold text-lg">{nome}</span>
            {cargo && <span className="text-sm font-light">{cargo}</span>}
        </div>
      </div>
      <div className="flex items-center gap-3">
        <span className="border-2 border-destaque rounded-full px-2 py-1 text-sm font-semibold">CPF 234.567.890-11</span>
        <span className="border-2 border-destaque rounded-full px-2 py-1 text-sm font-semibold">Internado</span>
      </div>
    </div>
  );
}

export default ListItem;
