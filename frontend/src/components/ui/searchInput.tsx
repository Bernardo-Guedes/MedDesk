    import { Search } from "lucide-react";

    interface SearchInputProps {
      value?: string;
      onChange?: (value: string) => void;
    }

    function SearchInput({ value, onChange }: SearchInputProps) {
    return (
        <div
        className="
            max-w-max
            ms-10 mb-8
            flex items-stretch
            rounded-xl
            border-2 border-borda
            bg-superficie-campo
            transition-all duration-200
            focus-within:border-destaque
            focus-within:bg-superficie
            focus-within:shadow-[0_0_0_3px_var(--sombra-destaque)]
        "
        >
        <span
            className="
            flex items-center justify-center
            border-r border-borda
            px-3.5
            text-principal
            "
        >
            <Search size={24} aria-hidden="true" />
        </span>

        <input
            type="search"
            className="
            min-w-0 flex-1
            rounded-none border-none
            bg-transparent
            px-4 py-3
            text-[15px] text-texto
            outline-none
            placeholder:text-texto-suave
            "
            placeholder="Busca por nome"
            autoComplete="off"
            value={value}
            onChange={onChange ? (event) => onChange(event.target.value) : undefined}
        />
        </div>
    );
    }

    export default SearchInput;