import FormAtendimento from "@/components/FormAtendimento";
import { FilePlusCorner } from "lucide-react";

function CriarAtendimento() {
  return (
    <div>
      <div className="bg-white p-5 flex gap-3 items-center text-3xl font-medium text-principal">
        <FilePlusCorner size={40} />
        Criar Atendimento
      </div>
      <FormAtendimento admin/>
    </div>
  );
}

export default CriarAtendimento;
