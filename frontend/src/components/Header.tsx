import logo from "../assets/images/logoMedDesk.png";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";

function Header({ profissional = false }: { profissional?: boolean }) {
  return (
    <header>
      <div className="flex items-center justify-between px-10 py-3 bg-header">
        <img src={logo} alt="Logo MedDesk" className="w-25 ms-8" />
        <div className="flex gap-3">
          <div className="flex flex-col items-center">
            <span className="text-white font-semibold text-lg">
              {profissional ? "Dr. Carlos Sales" : "Ana Souza"}
            </span>
            <span className="text-white font-semibold text-lg">
              {profissional ? "Pediatra" : "Secretária/Recepção"}
            </span>
          </div>
          <Avatar className="size-14 shrink-0 shadow-sm border-2 border-destaque">
            <AvatarImage src="https://randomuser.me/api/portraits/men/32.jpg" />
            <AvatarFallback className="bg-gray-200 text-gray-500 text-5xl font-semibold">
              BG
            </AvatarFallback>
          </Avatar>
        </div>
      </div>
    </header>
  );
}

export default Header;
