import ListItem from "@/components/ListItem";
import SearchInput from "@/components/ui/searchInput";
import { UserShield } from "lucide-react";

function Profissionais() {
  return (
    <div>
      <div className="bg-white p-5 flex gap-3 items-center text-3xl font-medium text-principal">
        <UserShield size={40} />
        Profissionais
      </div>
      <div className="mt-8">
        <SearchInput/>
        <ListItem nome="Dr. Carlos Sales" cargo="Pediatra"/>
        <ListItem nome="Dr. Roberto Almeida" cargo="Ortopedia"/>
        <ListItem nome="Dr. Augusto Faria" cargo="Cirurgião"/>
      </div>
    </div>
  );
}

export default Profissionais;
