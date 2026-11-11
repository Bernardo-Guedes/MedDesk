import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import StatusAtendimento, {
  type TipoStatusAtendimento,
} from "@/components/ui/StatusAtendimento";

interface PatientHeaderProps {
  nome: string;
  iniciais: string;
  idade: string;
  status?: string;
  statusAtendimento?: TipoStatusAtendimento;
}

function PatientHeader({
  nome,
  iniciais,
  idade,
  status,
  statusAtendimento = "neutro",
}: PatientHeaderProps) {
  return (
    <div className="flex flex-col gap-6 sm:flex-row sm:items-center">
      <Avatar className="size-24 shrink-0 border-2 border-destaque">
        <AvatarFallback className="bg-blue-100 text-3xl font-bold text-principal">
          {iniciais}
        </AvatarFallback>
      </Avatar>
      <div>
        <h3 className="text-2xl font-bold text-slate-800">{nome}</h3>
        <div className="mt-2 flex flex-wrap gap-2">
          <StatusAtendimento texto={idade} />
          {status && (
            <StatusAtendimento texto={status} status={statusAtendimento} />
          )}
        </div>
      </div>
    </div>
  );
}

export default PatientHeader;
