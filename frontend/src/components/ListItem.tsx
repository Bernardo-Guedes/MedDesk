import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import type { ReactNode } from "react";


interface ListItemProps {
  nome: string;
  cargo?: string;
  detalhes?: string[];
  imagem?: string | null;
  compacto?: boolean;
  destaque?: ReactNode;
}

function ListItem({
  nome,
  cargo,
  detalhes = ["CPF 234.567.890-11"],
  imagem = "https://randomuser.me/api/portraits/women/32.jpg",
  compacto = false,
  destaque,
}: ListItemProps) {
  return (
    <div className={`flex flex-wrap justify-between items-center gap-3 px-5 py-3 bg-white max-h-max ${compacto ? "rounded-3xl sm:rounded-full" : "rounded-full mx-10 mb-4"}`}>
      <div className="flex min-w-0 gap-4 items-center">
        <Avatar className="size-14 shrink-0 shadow-sm border-2 border-destaque">
          {imagem && <AvatarImage src={imagem} />}
          <AvatarFallback className={`bg-gray-200 text-gray-500 font-semibold ${compacto ? "text-xl" : "text-5xl"}`}>
            {nome
              .split(" ")
              .map((parte) => parte[0])
              .slice(0, 2)
              .join("")
              .toUpperCase()}
          </AvatarFallback>
        </Avatar>
        <div className="flex flex-col">
            <span className="font-bold text-lg">{nome}</span>
            {cargo && <span className="text-sm font-semibold">{cargo}</span>}
        </div>
      </div>
      <div className="flex flex-wrap items-center gap-3">
        {destaque}
        {detalhes.map((detalhe) => (
          <span key={detalhe} className="border-2 border-destaque rounded-full px-2 py-1 text-sm font-semibold">
            {detalhe}
          </span>
        ))}
      </div>
    </div>
  );
}

export default ListItem;
