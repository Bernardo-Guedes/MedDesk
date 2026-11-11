import { Select } from "@base-ui/react/select";
import { Check, ChevronDown } from "lucide-react";

interface SelectFiltroProps {
  id: string;
  rotulo: string;
  valor: string;
  onChange: (valor: string) => void;
  opcoes: { valor: string; texto: string }[];
}

function SelectFiltro({ id, rotulo, valor, onChange, opcoes }: SelectFiltroProps) {
  return (
    <div className="flex min-w-0 flex-col gap-2 sm:min-w-56">
      <label id={`${id}-rotulo`} htmlFor={id} className="text-sm font-semibold text-slate-700">
        {rotulo}
      </label>
      <Select.Root
        value={valor}
        onValueChange={(novoValor) => {
          if (novoValor !== null) onChange(novoValor);
        }}
        items={opcoes.map((opcao) => ({ value: opcao.valor, label: opcao.texto }))}
      >
        <Select.Trigger id={id} aria-labelledby={`${id}-rotulo`} className="flex w-full items-center justify-between gap-4 rounded-xl border border-slate-200 bg-slate-50 px-4 py-3 text-left text-sm font-medium text-slate-700 transition-colors hover:border-destaque hover:bg-white focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-destaque">
          <Select.Value />
          <Select.Icon><ChevronDown size={18} aria-hidden="true" className="text-principal" /></Select.Icon>
        </Select.Trigger>
        <Select.Portal>
          <Select.Positioner sideOffset={6} alignItemWithTrigger={false} className="z-50">
            <Select.Popup className="min-w-[var(--anchor-width)] max-h-[var(--available-height)] overflow-y-auto rounded-xl border border-slate-200 bg-white p-1.5 shadow-lg outline-none">
              <Select.List>
                {opcoes.map((opcao) => (
                  <Select.Item key={opcao.valor} value={opcao.valor} className="flex cursor-pointer items-center justify-between gap-4 rounded-lg px-3 py-2.5 text-sm text-slate-700 outline-none data-[highlighted]:bg-blue-50 data-[highlighted]:text-blue-800 data-[selected]:font-semibold">
                    <Select.ItemText>{opcao.texto}</Select.ItemText>
                    <Select.ItemIndicator><Check size={16} aria-hidden="true" className="text-principal" /></Select.ItemIndicator>
                  </Select.Item>
                ))}
              </Select.List>
            </Select.Popup>
          </Select.Positioner>
        </Select.Portal>
      </Select.Root>
    </div>
  );
}

export default SelectFiltro;
