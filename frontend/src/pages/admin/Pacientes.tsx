import ListItem from "@/components/ListItem";
import { User } from "lucide-react";
import SearchInput from "@/components/ui/searchInput"

function Pacientes() {
  return (
    <div>
      <div className="bg-white p-5 flex gap-3 items-center text-3xl font-medium text-principal">
        <User size={40} />
        Pacientes
      </div>
      <div className="mt-8">
        <SearchInput/>
        <ListItem nome="Alice Martins" />
        <ListItem nome="Luiza Gomes" />
        <ListItem nome="Mariana Costa" />
      </div>
    </div>
  );
}

export default Pacientes;
